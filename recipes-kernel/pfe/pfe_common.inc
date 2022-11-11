# Copyright 2019-2022 NXP
#
# PFE common recipe for Master/Slave

inherit module deploy

SUMMARY = "Linux driver for the Packet Forwarding Engine hardware"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE-GPL2.txt;md5=5dcdfe25f21119aa5435eab9d0256af7"

URL ?= "git://source.codeaurora.org/external/autobsps32/extra/pfeng;protocol=https;branch=master"
SRC_URI = "${URL}"

SRC_URI:append = " \
        file://bsp35/rc4/0001-version-PFE_S32G_A53_LNX_RTM_1.2.0-RC1.patch \
        file://0001-pfe_compiler-add-GCC-version-10.2.0-support.patch \
        file://0001-fix-hwts-kmemleak.patch \
        file://0001-pfe-sw-drop-the-unneeded-codes-to-fix-build-errors.patch \
        file://0001-pfe-netif-use-the-correct-ndo_eth_ioctl-to-fix-the-p.patch \
        file://0001-pfe-set-mac_managed_pm-to-true-to-fix-mdio-resume-wa.patch \
        file://0001-pfe-fix-the-lock-inconsistent-warning-when-rmmod-the.patch \
        file://0001-pfe-update-to-fix-the-possible-circular-locking.patch \
        file://0001-pfe-fix-the-kernel-calltrace-when-dhcp-for-pfe-ports.patch \
        file://${PFE_LOCAL_FIRMWARE_DIR}\
"

SRCREV = "e7cce64f125892f41cd1695ba5d08e3b5d3e2704"

PATCHTOOL = "git"

S = "${WORKDIR}/git"
MDIR = "${S}/sw/linux-pfeng"
INSTALL_DIR = "${D}/${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/ethernet/nxp/pfe"

EXTRA_OEMAKE:append = " KERNELDIR=${STAGING_KERNEL_DIR} MDIR=${MDIR} -C ${MDIR} V=1 drv-build"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
