# Copyright 2019-2022 NXP
#
# PFE common recipe for Master/Slave

inherit module deploy

SUMMARY = "Linux driver for the Packet Forwarding Engine hardware"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE-GPL2.txt;md5=5dcdfe25f21119aa5435eab9d0256af7"

URL ?= "git://source.codeaurora.org/external/autobsps32/extra/pfeng;protocol=https;branch=master"
SRC_URI = "${URL}"

SRCREV = "0669f53e2cc0b3c133406e3db2ae72f5b3934e5f"

PATCHTOOL = "git"

S = "${WORKDIR}/git"
MDIR = "${S}/sw/linux-pfeng"
INSTALL_DIR = "${D}/${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/ethernet/nxp/pfe"

EXTRA_OEMAKE:append = " KERNELDIR=${STAGING_KERNEL_DIR} MDIR=${MDIR} -C ${MDIR} V=1 drv-build"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
