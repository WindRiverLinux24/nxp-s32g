# Copyright 2019-2022 NXP
#
# PFE common file for Master/Slave driver and libfci components

SUMMARY = "Linux driver for the Packet Forwarding Engine hardware"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE-GPL2.txt;md5=5dcdfe25f21119aa5435eab9d0256af7"

URL ?= "git://github.com/nxp-auto-linux/pfeng.git;protocol=https;branch=master"
SRC_URI = "${URL}"


SRCREV = "caf8de596a5a5dcb5c23c16c9271a8536b69c9f8"

SRC_URI += " \
    file://bsp36/rc5/0001-version-PFE_S32G_A53_LNX_1.3.0-RC1.patch \
    file://bsp36/rc5/0002-f9c1fd1b253ea8c8a668d18f5a302acffcc42d47.patch \
"

PATCHTOOL = "git"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
