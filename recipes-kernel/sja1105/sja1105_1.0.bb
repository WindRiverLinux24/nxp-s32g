# Copyright 2017-2020 NXP

SUMMARY = "Add support for SJA1105 switch for S32V234EVB, BB Mini, S32G-PROCEVB-S plus S32GRV-PLATEVB and S32G-VNP-RDB"
LICENSE = "GPLv2+ & MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit module python3native

URL ?= "git://source.codeaurora.org/external/autobsps32/sja1105x;protocol=https"
BRANCH ?= "hotfix/bsp29.0.hf1"
SRC_URI = "${URL};branch=${BRANCH}"
SRCREV = "2083f642759d039f0e107bc856a29cc127aa67ca"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += "\
    file://0001-sja1105-module-Makefile-didn-t-respect-install-dirs.patch \
    file://0001-sja-Update-sja-for-kernel-5.10.patch \
"

S = "${WORKDIR}/git"
DESTDIR = "${D}"
MDIR = "${S}"
INSTALL_DIR = "${D}/${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/spi"
EXTRA_OEMAKE:append = " INSTALL_DIR=${DESTDIR} KERNELDIR=${KBUILD_OUTPUT} MYCOMPILER=${CROSS_COMPILE}gcc "
EXTRA_OEMAKE:append:nxp-s32g2xx = " MYPLATFORM=gplat "

SJA1105_MOD_NAME = "sja1105pqrs.ko"

module_do_install() {

	mkdir -p ${INSTALL_DIR}
	install -D ${MDIR}/${SJA1105_MOD_NAME} ${INSTALL_DIR}/
}

FILES:${PN} += "${base_libdir}/*"
FILES:${PN} += "${sysconfdir}/modules-load.d/*"

PROVIDES += "kernel-module-sja1105pqrs"
RPROVIDES:${PN} += "kernel-module-sja1105pqrs"

COMPATIBLE_MACHINE = "nxp-s32g2xx"
INHIBIT_PACKAGE_STRIP = "1"

DEPENDS:append = " coreutils-native"
