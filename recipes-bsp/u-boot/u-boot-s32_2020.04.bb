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
DEPENDS_append = " libgcc virtual/${TARGET_PREFIX}gcc python3 dtc-native bison-native"

inherit nxp-u-boot-localversion

SRC_URI_prepend = "git://source.codeaurora.org/external/autobsps32/u-boot;protocol=https;branch=release/bsp28.0-2020.04 "

SRC_URI += " \
    file://0001-dts-s32g-modify-the-hse_reserved-memory-node-to-comp.patch \
    file://0001-configs-s32g274aevb-add-HSE_SECBOOT-config-for-HSE-t.patch \
    file://0001-secboot-add-key-store-status-check-point-after-sys_i.patch \
    file://0001-Make-s32g274ardb2-and-s32g2xxaevb-support-ostree.patch \
    file://0001-scripts-mailmapper-python2-python3.patch \
    file://sdk29/0001-boards-campp-update-lpddr2-settings.patch \
    file://sdk29/0002-boards-campp-add-rev-b-board-support.patch \
    file://sdk29/0003-configs-bluebox3-disable-dram-inline-ecc.patch \
    file://sdk29/0004-s32g398_emu-Add-initial-support.patch \
    file://sdk29/0005-clk-s32g389a-Update-core-frequency-to-1.3GHz.patch \
    file://sdk29/0006-clk-s32-Fix-DFS-mfn-formula.patch \
    file://sdk29/0007-clk-Rate-is-defined-as-ulong-not-int.patch \
    file://sdk29/0008-fsl_esdhc_imx-Fix-compile-error-when-MMC_SUPPORTS_TU.patch \
    file://sdk29/0009-s32g-ethernet-add-alternative-way-to-init-PFE-FW.patch \
    file://sdk29/0010-image-Fix-compilation-error-when-building-without-a-.patch \
    file://sdk29/0011-clk-s32gen1-Correct-GMAC-SGMII-clock.patch \
    file://sdk29/0012-s32gen1-Add-fdt_pcie_spis_fixup-in-env.patch \
    file://sdk29/0013-s32g-clk-Define-ARM_PLL-DFS1-frequency.patch \
    file://sdk29/0014-arch-s32g-DTS-refactoring.patch \
    file://sdk29/0015-s32g3-dts-Update-mc_cgm0-definition.patch \
    file://sdk29/0016-s32g3-include-Define-MC_CGM6_MUXn.patch \
    file://sdk29/0017-s32g3-dts-Add-mc_cgm6-node.patch \
    file://sdk29/0018-s32g-clk-Split-s32g3-from-s32g2.patch \
    file://sdk29/0019-s32g-clk-Use-S32G-instead-of-S32G274.patch \
    file://sdk29/0020-s32g3-clk-Add-support-MC_CGM6.patch \
    file://sdk29/0021-u-boot-s32-Fix-build-Warnings-on-clocking-framework.patch \
    file://sdk29/0022-s32g3-clk-Add-support-for-GMAC-clocks.patch \
    file://sdk29/0023-s32g-Use-CMU-just-for-hardware.patch \
    file://sdk29/0024-s32-Fix-CONFIG_S32_CMU-dependency.patch \
    file://sdk29/0025-s32g3-Add-support-for-CMU.patch \
    file://sdk29/0026-s32g-Activate-periph-clocks-for-s32g-emulator.patch \
    file://sdk29/0027-fdt-scmi-Add-dts-fixup-for-arm-scmi-smc-node.patch \
    file://sdk29/0028-s32-gen1-flashmap-Update-Linux-dtb-rootfs-offsets.patch \
    file://sdk29/0029-secboot-update-to-hse-fw-0.9.0.patch \
    file://sdk29/0030-s32v234-flashmap-Increase-Kernel-fdt-and-rootfs-offs.patch \
    file://sdk29/0031-s32-Lower-the-value-of-initrd_high.patch \
    file://sdk29/0032-clk-scmi-Correct-return-of-set_rate-callback.patch \
    file://sdk29/0033-s32g-configs-Enable-E1000-for-S32G-RDB1-boards.patch \
    file://sdk29/0034-s32-Remove-duplicated-run-loadimage-command.patch \
    file://sdk29/0035-s32g-fdt-Amend-SCMI-reset-controller-settings.patch \
    file://sdk29/0036-s32g-fdt-Amend-SerDes-settings.patch \
    file://sdk29/0037-mtd-spi-nor-core-Add-full-chip-erase-method.patch \
    file://sdk29/0038-pci-s32gen1-Enable-PCIe-coherent-transactions.patch \
    file://sdk29/0039-pfe-fixup-dma-coherent-to-support-both-Rev1-and-Rev2.patch \
    file://sdk29/0040-pfe-fixup-compatible-string-to-support-both-Rev1-and.patch \
    file://sdk29/0041-config-s32-remove-DDR-size-limitation.patch \
    file://sdk29/0042-secboot-update-hse-interface-and-hse_reserved.patch \
"

SRCREV = "eef88755a719c802f9dbfceaa06190abb96e74d1"
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
do_install_append() {

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

do_compile_append() {

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

COMPATIBLE_MACHINE_nxp-s32g2xx = "nxp-s32g2xx"
