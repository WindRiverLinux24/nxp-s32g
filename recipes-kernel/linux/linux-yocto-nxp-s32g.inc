#
# Copyright (C) 2020 Wind River Systems, Inc.
#

COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
TARGET_SUPPORTED_KTYPES:nxp-s32g = "standard preempt-rt"

KERNEL_VERSION_SANITY_SKIP = "1"
SRCREV_machine:nxp-s32g ?= "${AUTOREV}"
SRCREV_meta:nxp-s32g ?= "${AUTOREV}"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append:nxp-s32g = " \
        file://0001-Revert-modpost-turn-missing-MODULE_LICENSE-into-erro.patch \
        file://0001-drivers-serdes-use-get_lane_id-to-get-the-right-id-f.patch \
"

