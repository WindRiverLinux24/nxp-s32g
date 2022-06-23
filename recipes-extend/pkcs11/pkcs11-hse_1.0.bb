DESCRIPTION = "NXP HSE PKCS#11 Module"
PROVIDES += "pkcs11-hse"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = " \
    file://README.md;md5=b451d36d865e4242aa2b944fb0370269 \
"

DEPENDS = "openssl libp11"

SRC_URI = "https://bitbucket.sw.nxp.com/projects/ALBW/repos/pkcs11-hse/pkcs11-hse.tar.gz"

SRCREV = "9d8a6339f521f009c4df1811b2ba9686d223c31d"
SRC_URI[sha256sum] = "87acf7f788f954bb4722aad9de02544a257af47349eababe7ed152767573c12c"

SRC_URI += " \
    file://0001-pkcs11-hse-Makefile-using-internal-compile-variables.patch \
"

PATCHTOOL = "git"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/pkcs11-hse"

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
}

PACKAGES =+ "${PN}-examples "
FILES:${PN}-examples += "${bindir}"
COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
