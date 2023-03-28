#
# Copyright (C) 2020 Wind River Systems, Inc.
#

COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
TARGET_SUPPORTED_KTYPES:nxp-s32g = "standard preempt-rt"

FILESEXTRAPATHS:prepend:nxp-s32g := "${THISDIR}/files:"
SRC_URI:append:nxp-s32g = " \
    file://0001-Revert-mmc-sdhci-esdhc-imx-Propagate-ESDHC_FLAG_HS40.patch \
"
