# Copyright 2019-2022 NXP
#
# PFE common file for Master/Slave driver and libfci components

SUMMARY = "Linux driver for the Packet Forwarding Engine hardware"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE-GPL2.txt;md5=5dcdfe25f21119aa5435eab9d0256af7"

URL ?= "git://github.com/nxp-auto-linux/pfeng.git;protocol=https;branch=release/linux_1.7.0"
SRC_URI = "${URL}"


SRCREV = "fa89bd271719146c6f6103a348ea727568504c25"

PATCHTOOL = "git"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
