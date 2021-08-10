#
# Copyright (C) 2020 Wind River Systems, Inc.
#

COMPATIBLE_MACHINE:nxp-s32g2xx = "nxp-s32g2xx"
TARGET_SUPPORTED_KTYPES:nxp-s32g2xx = "standard"

KERNEL_VERSION_SANITY_SKIP = "1"
SRCREV_machine:nxp-s32g2xx ?= "${AUTOREV}"
SRCREV_meta:nxp-s32g2xx ?= "${AUTOREV}"
