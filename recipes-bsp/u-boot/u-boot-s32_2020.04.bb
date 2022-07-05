require recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "U-boot provided by NXP with focus on S32 chipsets"
PROVIDES += "u-boot"

LICENSE = "GPL-2.0-only & BSD-3-Clause & BSD-2-Clause & LGPL-2.0-only & LGPL-2.1-only"
LIC_FILES_CHKSUM = " \
    file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://Licenses/bsd-2-clause.txt;md5=6a31f076f5773aabd8ff86191ad6fdd5 \
    file://Licenses/bsd-3-clause.txt;md5=4a1190eac56a9db675d58ebe86eaf50c \
    file://Licenses/lgpl-2.0.txt;md5=5f30f0716dfdd0d91eb439ebec522ec2 \
    file://Licenses/lgpl-2.1.txt;md5=4fbd65380cdd255951079008b364516c \
"

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS:append = " libgcc virtual/${TARGET_PREFIX}gcc python3 dtc-native bison-native"

inherit nxp-u-boot-localversion

SRC_URI:prepend = "git://source.codeaurora.org/external/autobsps32/u-boot;protocol=https;branch=release/bsp33.0-2020.04"
SRC_URI += " \
    file://0001-scripts-mailmapper-python2-python3.patch \
    file://0001-tools-s32gen1_secboot-replace-u-boot.s32-with-u-boot.patch \
    file://0001-tools-s32ccimage-add-reserved-member-for-struct-prog.patch \
    file://0001-u-boot-s32-Makefile-add-scripts_basic-dependency-to-.patch \
    file://0001-configs-Enable-commands-for-ostree.patch \
    file://0001-include-config_distro_bootcmd.h-Check-go-before-boot.patch \
    file://0001-s32g-remove-SAF1508-phy-driver-and-use-common-ulpi-i.patch \
    ${@bb.utils.contains('HSE_SEC_ENABLED', '1', 'file://0001-configs-s32g2xxaevb-add-HSE_SECBOOT-config.patch', '', d)} \
"

SRCREV = "9cdfca686e27add82d8f23e2ef9bd86c1270e137"
SRC_URI[sha256sum] = "4e80caf195787c76639675712492230d090fe2eb435fd44491d653463485e30c"

SCMVERSION = "y"
LOCALVERSION = ""
PACKAGE_ARCH = "${MACHINE_ARCH}"
UBOOT_INITIAL_ENV = ""

USRC ?= ""
S = '${@oe.utils.conditional("USRC", "", "${WORKDIR}/git", "${USRC}", d)}'


do_compile:append() {
    cfgout="${B}/s32g2xxaevb_defconfig/u-boot-s32.cfgout"
    if [ "${HSE_SEC_ENABLED}" = "1"  -a -e $cfgout ]; then
        sed -i 's|${HSE_FW_DEFAULT_NAME}|${HSE_LOCAL_FIRMWARE_DIR}/${HSE_FW_NAME_S32G2}|g' $cfgout
    fi
}

do_deploy:append() {
    unset i j
    for config in ${UBOOT_MACHINE}; do
        i=$(expr $i + 1);
        for type in ${UBOOT_CONFIG}; do
            j=$(expr $j + 1)
            if  [ $j -eq $i ]; then
                install -d ${DEPLOYDIR}/${type}/tools
                install -m 0644 ${B}/${config}/${UBOOT_BINARY} ${DEPLOYDIR}/${type}/${UBOOT_BINARY}
                install -m 0644 ${B}/${config}/${UBOOT_CFGOUT} ${DEPLOYDIR}/${type}/tools/${UBOOT_CFGOUT}
                install -m 0755 ${B}/${config}/tools/mkimage ${DEPLOYDIR}/${type}/tools/mkimage
            fi
        done
        unset j
    done
    unset i
}


COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
