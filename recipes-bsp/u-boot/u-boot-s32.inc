require recipes-bsp/u-boot/u-boot-common.inc

PE = ""

LICENSE = "GPL-2.0-only & BSD-3-Clause & BSD-2-Clause & LGPL-2.0-only & LGPL-2.1-only"
LIC_FILES_CHKSUM = " \
    file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://Licenses/bsd-2-clause.txt;md5=6a31f076f5773aabd8ff86191ad6fdd5 \
    file://Licenses/bsd-3-clause.txt;md5=4a1190eac56a9db675d58ebe86eaf50c \
    file://Licenses/lgpl-2.0.txt;md5=5f30f0716dfdd0d91eb439ebec522ec2 \
    file://Licenses/lgpl-2.1.txt;md5=4fbd65380cdd255951079008b364516c \
"

SRC_URI = "git://source.codeaurora.org/external/autobsps32/u-boot;protocol=https;branch=release/bsp34.0-2020.04"
SRC_URI += " \
    file://0001-scripts-mailmapper-python2-python3.patch \
    file://0001-tools-s32gen1_secboot-replace-u-boot.s32-with-u-boot.patch \
    file://0001-tools-s32ccimage-add-reserved-member-for-struct-prog.patch \
    file://0001-u-boot-s32-Makefile-add-scripts_basic-dependency-to-.patch \
    file://0001-configs-Enable-commands-for-ostree.patch \
    file://0001-include-config_distro_bootcmd.h-Check-go-before-boot.patch \
    file://0001-s32g-remove-SAF1508-phy-driver-and-use-common-ulpi-i.patch \
    ${@bb.utils.contains('HSE_SEC_ENABLED', '1', 'file://0001-configs-s32g2xxaevb-add-HSE_SECBOOT-config.patch', '', d)} \
    ${@bb.utils.contains('HSE_SEC_ENABLED', '1', 'file://0001-configs-s32g274ardb2-add-HSE_SECBOOT-config.patch', '', d)} \
    ${@bb.utils.contains('HSE_SEC_ENABLED', '1', 'file://0002-configs-s32g399ardb3-add-HSE_SECBOOT-config.patch', '', d)} \
    ${@bb.utils.contains('HSE_SEC_ENABLED', '1', 'file://0003-configs-s32g3xxaevb-add-HSE_SECBOOT-config.patch', '', d)} \
    file://0001-s32ccimage.c-Use-PATH_MAX-for-path-length.patch \
"

SRCREV = "6391b468b1cf1e95b1f8020fadc59733c510eab6"

SCMVERSION = "y"
LOCALVERSION = ""
PACKAGE_ARCH = "${MACHINE_ARCH}"
UBOOT_INITIAL_ENV = ""

USRC ?= ""
S = '${@oe.utils.conditional("USRC", "", "${WORKDIR}/git", "${USRC}", d)}'

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"

DEFAULT_PREFERRENCE = "-1"
