DESCRIPTION = "NXP HSE PKCS#11 Module"
PROVIDES += "pkcs11-hse"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = " \
    file://README.md;md5=b451d36d865e4242aa2b944fb0370269 \
"

DEPENDS = "openssl libp11"

SRC_URI = "https://bitbucket.sw.nxp.com/projects/ALBW/repos/pkcs11-hse/pkcs11-hse.tar.gz"

SRCREV = "9acdc9238a248a0d2e4b3aa71d5b11149fef8f01"
SRC_URI[sha256sum] = "b529fcbbb8f4347310d433162b81291da5955f9916d5c6ad5f4dc316ef6aef14"

PKCS_BSP36_RC5_PATCHES = " \
    file://bsp36/rc5/0001-examples-refactor-examples-build-structure.patch \
    file://bsp36/rc5/0002-pkcs11-hse-add-examples-target-in-top-directory-Make.patch \
    file://bsp36/rc5/0003-pkcs11-hse-add-versioning-for-libpkcs-hse-library.patch \
    file://bsp36/rc5/0004-pkcs11-hse-add-install-Makefile-rule.patch \
    file://bsp36/rc5/0005-examples-add-TrustZone-Key-Provisioning-feature.patch \
    file://bsp36/rc5/0006-examples-make-all-builds-only-default-examples.patch \
    file://bsp36/rc5/0007-examples-add-userspace-key-provisioning-using-the-TE.patch \
    file://bsp36/rc5/0008-examples-generate-kek-encrypted-keys-on-the-host-mac.patch \
    file://bsp36/rc5/0009-Add-Apache-v2.0-License-OpenSSL-Copyright-Notice.patch \
"

SRC_URI += " \
    ${PKCS_BSP36_RC5_PATCHES} \
    file://0001-pkcs11-hse-Makefile-using-internal-compile-variables.patch \
    file://0001-hse-initialize-used-field-of-struct-node_data.patch \
    file://0001-hse-pkcs-secboot-replace-memcpy-with-specific-hse_me.patch \
    file://0001-Makefile-Make-examples-depend-on-libhse.so-to-fix-a-.patch \
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


    plats="s32g2 s32g3"
    bins="pkcs-keyop hse-encrypt hse-sysimg pkcs-key-provision hse-secboot"
    for plat in $plats; do

        # compile share libraries(libhse and libpkcs) firstly, they are all same either S32G2 or S32G3
        oe_runmake HSE_FWDIR=${S}/hse-fw/${plat}  CFLAGS="${CFLAGS} -shared -fPIC -Wall -fno-builtin"

        # clean the example binaries because they are needed to be compiled with different options
        oe_runmake -C examples clean
        # compile demo apps which may not be same between S32G2 and S32G3
        oe_runmake -C examples HSE_FWDIR=${S}/hse-fw/${plat} PKCS11HSE_DIR=${S} LIBS="-L${STAGING_LIBDIR}/" INCLUDE="-I${STAGING_INCDIR}" LDFLAGS="${LDFLAGS} -lcrypto -lp11"

        #copy result files to related dir
        mkdir -p ${S}/examples/${plat}

        for bin in ${bins}; do
            cp ${S}/examples/${bin}/${bin} ${S}/examples/${plat}
        done

    done
}

do_install() {

    install -d ${D}${libdir}
    install -m 0755 ${S}/libpkcs-hse.so.1.0 ${D}${libdir}/libpkcs-hse.so.1.0
    install -m 0755 ${S}/libhse.so.1.0 ${D}${libdir}/libhse.so.1.0
    ln -s libhse.so.1.0 ${D}${libdir}/libhse.so.1

    install -d ${D}${includedir}
    install -m 0644 ${S}/libhse/*.h ${D}${includedir}
    install -m 0644 ${S}/libpkcs/*.h ${D}${includedir}

    plats="s32g2 s32g3"
    bins="pkcs-keyop hse-encrypt hse-sysimg pkcs-key-provision hse-secboot"
    for plat in $plats; do

        install -d ${D}${bindir}/${plat}/
        for bin in ${bins}; do
            install -m 0755 ${S}/examples/${plat}/${bin} ${D}${bindir}/${plat}/
        done

    done
}

pkg_postinst_ontarget:${PN}() {

bins="pkcs-keyop hse-encrypt hse-sysimg pkcs-key-provision hse-secboot"
if grep -q "s32g3" /sys/firmware/devicetree/base/compatible ; then
    plat="s32g3"
else
    plat="s32g2"
fi

echo  "plat is ${plat}"
for bin in ${bins}; do
    if [ -f "/usr/bin/${bin}" ]; then
        continue
    fi

    cp /usr/bin/${plat}/${bin} /usr/bin/${bin}
done

# remove the unneeded directories
rm -rf /usr/bin/s32g2
rm -rf /usr/bin/s32g3

}

PACKAGES =+ "${PN}-examples "
FILES:${PN}-examples += "${bindir}"
COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
