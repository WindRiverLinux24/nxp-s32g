require recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "U-boot provided by NXP with focus on S32 chipsets"
PROVIDES += "u-boot"

LICENSE = "GPLv2 & BSD-3-Clause & BSD-2-Clause & LGPL-2.0 & LGPL-2.1"
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

SRC_URI:prepend = "git://source.codeaurora.org/external/autobsps32/u-boot;protocol=https;branch=release/bsp30.0-2020.04 "

SRC_URI += " \
    file://0001-configs-s32g274aevb-add-HSE_SECBOOT-config-for-HSE-t.patch \
    file://0001-secboot-add-key-store-status-check-point-after-sys_i.patch \
    file://0001-Make-s32g274ardb2-and-s32g2xxaevb-support-ostree.patch \
    file://0001-scripts-mailmapper-python2-python3.patch \
    file://bsp31/rc1/0001-s32v234hpcsom-add-initial-board-support.patch \
    file://bsp31/rc1/0002-s32v234hpcsom-add-defconfig.patch \
    file://bsp31/rc1/0003-s32v234hpcsom-add-ddr-config-for-hpc-som-board.patch \
    file://bsp31/rc1/0004-clk-s32-gen1-Set-the-frequency-to-the-nearest-availa.patch \
    file://bsp31/rc1/0005-s32r45-SoC-Print-SoC-revision-Major.Minor.patch \
    file://bsp31/rc2/0001-misc-s32gen1-Enable-all-fuse-subcommands.patch \
    file://bsp31/rc2/0002-s32-gen1-Make-s32gen1-config-nodes-subnodes-of-s32ge.patch \
    file://bsp31/rc2/0003-clk-s32gen1-Remove-unnecessary-partition-blocks-for-.patch \
    file://bsp31/rc2/0004-s32-Disable-ECC-region-exclusion-for-S32GEN1-emulato.patch \
    file://bsp31/rc2/0005-s32g3-Correct-the-way-MC_ME.PRTN0_CORE-offset-is-cal.patch \
    file://bsp31/rc2/0006-s32g3-Correct-the-calculation-of-MC_RGM.PRST1_0-fiel.patch \
    file://bsp31/rc2/0007-s32g3-Add-initial-support-for-s32g3-evb-board.patch \
    file://bsp31/rc2/0008-dt-bindings-clock-Run-DDR-at-800MHz-for-all-S32GEN1-.patch \
    file://bsp31/rc2/0009-s32g2-Remove-Rev1-leftovers.patch \
    file://bsp31/rc2/0010-s32g-Renamme-TARGET_S32G274AEVB-to-TARGET_S32G2XXAEV.patch \
    file://bsp31/rc2/0011-s32g-Remove-MSCM-initialization.patch \
    file://bsp31/rc2/0012-pcie-s32g-Fix-BARx_MASK-register-addresses.patch \
    file://bsp31/rc2/0013-s32-pcie-Update-link-speed-info-for-S32G3-platform.patch \
    file://bsp31/rc2/0014-sja1105-increase-clock-frequency-to-4MHz.patch \
    file://bsp31/rc2/0015-sja1105-move-driver-to-the-misc-category.patch \
    file://bsp31/rc2/0016-sja1105-add-dm-support.patch \
    file://bsp31/rc2/0017-s32g274aevb-probe-sja1105-in-misc_init_r.patch \
    file://bsp31/rc2/0018-s32r45-Reset-PHY-and-reapply-GMAC-clock-settings.patch \
    file://bsp31/rc2/0019-s32g2evb-Rename-linux-dtb-to-fsl-s32g2xxa-evb.dtb.patch \
"

SRCREV = "7eba18e1c0b994180e173e9343c7fe50819d9732"
SRC_URI[sha256sum] = "4e80caf195787c76639675712492230d090fe2eb435fd44491d653463485e30c"

SCMVERSION = "y"
LOCALVERSION = ""
PACKAGE_ARCH = "${MACHINE_ARCH}"
UBOOT_INITIAL_ENV = ""

USRC ?= ""
S = '${@oe.utils.conditional("USRC", "", "${WORKDIR}/git", "${USRC}", d)}'

# Enable Arm Trusted Firmware
SRC_URI += " \
    ${@bb.utils.contains('ATF_S32G_ENABLE', '1', 'file://0001-defconfig-add-support-of-ATF-for-rdb2-boards.patch', '', d)} \
"

# For now, only rdb2 boards support ATF, this function will be fixed when new ATF supported boards added.
do_install:append() {

    if [ -n "${ATF_S32G_ENABLE}" ]; then
        unset i j
        install -d ${DEPLOY_DIR_IMAGE}
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1)
                if  [ $j -eq $i ]; then
                        if [ "$type" = "${ATF_SUPPORT_TYPE}" ]; then
                            cp ${B}/${config}/u-boot.bin ${DEPLOY_DIR_IMAGE}/u-boot.bin
                            install -d ${DEPLOY_DIR_IMAGE}/tools
                            cp ${B}/${config}/tools/mkimage ${DEPLOY_DIR_IMAGE}/tools/mkimage
                            break
                        fi
                fi
            done
            unset j
        done
        unset i
    fi
}

# Modify the layout of u-boot to adding hse support using the following script.
# Currentlly, the board version of EVB is rev 1.0 and RDB2 is rev 2.0, they need
# different hse firmware version to coorperate with the board version, and these
# two boards will use same board version in future.

HSE_LOCAL_FIRMWARE_EVB_BIN ?= ""
HSE_LOCAL_FIRMWARE_RDB2_BIN ?= ""

do_compile:append() {

    unset i j
    for config in ${UBOOT_MACHINE}; do
	cp ${B}/tools/s32gen1_secboot.sh ${B}/${config}/tools/s32gen1_secboot.sh
        chmod +x ${B}/${config}/tools/s32gen1_secboot.sh

	i=$(expr $i + 1);
	for type in ${UBOOT_CONFIG}; do
		j=$(expr $j + 1)
		if  [ $j -eq $i ]; then

			if [ "${config}" = "${S32G274AEVB_UBOOT_DEFCONFIG_NAME}" ]; then
				if [ -n "${HSE_LOCAL_FIRMWARE_EVB_BIN}" ] && [ -e "${HSE_LOCAL_FIRMWARE_EVB_BIN}" ]; then
					${B}/${config}/tools/s32gen1_secboot.sh -k ./keys_hse -d ${B}/${config}/u-boot-hse-${type}.s32 --hse ${HSE_LOCAL_FIRMWARE_EVB_BIN}
					cp ${B}/${config}/u-boot-hse-${type}.s32 ${B}/${config}/u-boot.s32
					cp ${B}/${config}/u-boot-hse-${type}.s32 ${B}/${config}/u-boot-${type}.bin
				fi
			else
				if [ -n "${HSE_LOCAL_FIRMWARE_RDB2_BIN}" ] && [ -e "${HSE_LOCAL_FIRMWARE_RDB2_BIN}" ]; then
					${B}/${config}/tools/s32gen1_secboot.sh -k ./keys_hse -d ${B}/${config}/u-boot-hse-${type}.s32 --hse ${HSE_LOCAL_FIRMWARE_RDB2_BIN}
					cp ${B}/${config}/u-boot-hse-${type}.s32 ${B}/${config}/u-boot.s32
					cp ${B}/${config}/u-boot-hse-${type}.s32 ${B}/${config}/u-boot-${type}.bin
				fi
			fi
		fi
	done
	unset j
    done
    unset i
}

COMPATIBLE_MACHINE:nxp-s32g2xx = "nxp-s32g2xx"
