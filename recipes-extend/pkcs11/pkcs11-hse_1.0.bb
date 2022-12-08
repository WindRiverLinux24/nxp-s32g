DESCRIPTION = "NXP HSE PKCS#11 Module"
PROVIDES += "pkcs11-hse"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = " \
    file://README.md;md5=b451d36d865e4242aa2b944fb0370269 \
"

DEPENDS = "openssl libp11"

SRC_URI = "https://bitbucket.sw.nxp.com/projects/ALBW/repos/pkcs11-hse/pkcs11-hse.tar.gz"

SRCREV = "4be61dbaa14b6383abb11fad46a8371966606b82"
SRC_URI[sha256sum] = "d864f6c2e0e238362f5e56e692da61eda2cee27c1383c77c90fa2be6fc989581"

SRC_URI += " \
    file://bsp35/rc7/0001-secboot-add-support-for-secboot-to-libhse-usrspc.patch \
    file://bsp35/rc7/0002-pkcs-fix-wrong-jump-label-on-error-case.patch \
    file://bsp35/rc9/0001-pkcs-fix-ec-key-import.patch \
    file://bsp35/rc9/0002-secboot-change-coreID-based-on-HSE_PLATFORM.patch \
    file://bsp35/rc9/0003-pkcs-fix-bus-error-when-writing-to-key_info.patch \
    file://bsp35/rc9/0004-hse-add-support-for-hse_memcpy-and-hse_memset.patch \
    file://bsp35/rc9/0005-hse-pkcs-secboot-set-hse-service-descriptors-to-0.patch \
    file://bsp35/rc9/0006-pkcs-add-support-for-digest-ops-w-SHA1.patch \
    file://bsp35/rc9/0007-pkcs-guard-EC-key-format-with-ifdef.patch \
    file://0001-pkcs11-hse-Makefile-using-internal-compile-variables.patch \
    file://0002-pkcs-fix-QA-error.patch \
"

PATCHTOOL = "git"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/pkcs11-hse"

# Disable -O2 optimization, since it seems to be exposing an alignment issue
SELECTED_OPTIMIZATION:remove = "-O2"

EXTRA_OEMAKE += " \
	CROSS_COMPILE=${TARGET_PREFIX} \
"

CFLAGS += "${HOST_CC_ARCH}${TOOLCHAIN_OPTIONS}"

do_compile() {
    oe_runmake HSE_FWDIR=${S}/hse-fw  CFLAGS="${CFLAGS} -shared -fPIC -Wall -fno-builtin"
    oe_runmake -C examples HSE_FWDIR=${S}/hse-fw LIBS="-L${STAGING_LIBDIR}/" INCLUDE="-I${STAGING_INCDIR}" LDFLAGS="${LDFLAGS} -lcrypto -lp11"
}

do_install() {
    install -d ${D}${libdir}

    install -m 0755 ${S}/libpkcs-hse.so ${D}${libdir}/libpkcs-hse.so.1.0
    ln -s libpkcs-hse.so.1.0 ${D}${libdir}/libpkcs-hse.so
    install -m 0755 ${S}/libhse.so.1.0 ${D}${libdir}/libhse.so.1.0
    ln -s libhse.so.1.0 ${D}${libdir}/libhse.so.1

    install -d ${D}${includedir}
    install -m 0644 ${S}/libhse/*.h ${D}${includedir}
    install -m 0644 ${S}/libpkcs/*.h ${D}${includedir}

    install -d ${D}${bindir}
    install -m 0755 ${S}/examples/pkcs-keyop ${D}${bindir}
    install -m 0755 ${S}/examples/hse-encrypt ${D}${bindir}
    install -m 0755 ${S}/examples/hse-sysimg ${D}${bindir}
    install -m 0755 ${S}/examples/pkcs-key-provision ${D}${bindir}
    install -m 0755 ${S}/examples/hse-secboot ${D}${bindir}
}

PACKAGES =+ "${PN}-examples "
FILES:${PN}-examples += "${bindir}"
COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
