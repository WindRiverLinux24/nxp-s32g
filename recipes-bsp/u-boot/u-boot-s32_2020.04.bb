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

SRC_URI:prepend = "git://source.codeaurora.org/external/autobsps32/u-boot;protocol=https;branch=release/bsp32.0-2020.04"
SRC_URI += " \
    file://bsp33/rc1/0001-s32-remove-S32V-leftovers-and-changes-not-needed.patch \
    file://bsp33/rc1/0002-s32-remove-unused-mmcd-header.patch \
    file://bsp33/rc1/0003-s32-remove-s32-gen1-regs-header.patch \
    file://bsp33/rc1/0004-nvme-rdbX-Enable-NVME-CMD-support.patch \
    file://bsp33/rc1/0005-arch-arm-dts-Change-LinFlex-compatible.patch \
    file://bsp33/rc1/0006-drivers-serial-Update-compatible-for-LinFlex.patch \
    file://bsp33/rc1/0007-drivers-serial-LinFlex-style-fixes.patch \
    file://bsp33/rc1/0008-arch-arm-cpu-Rename-CONFIG_SYS_FSL_PERIPH_BASE-to-PE.patch \
    file://bsp33/rc1/0009-arch-arm-cpu-Rename-CONFIG_SYS_FSL_DRAM_-to-PHYS_SDR.patch \
    file://bsp33/rc1/0010-arch-arm-cpu-Fix-style-issues.patch \
    file://bsp33/rc1/0011-include-configs-Remove-CONFIG_SYS_INIT_RAM_.patch \
    file://bsp33/rc1/0012-board-freescale-s32-gen1-Update-SYS_DATA_BASE-s-desc.patch \
    file://bsp33/rc1/0013-board-Change-the-vendor-of-S32GEN1-board.patch \
    file://bsp33/rc1/0014-configs-Fix-S32G274A-BlueBox-compilation.patch \
    file://bsp33/rc1/0015-arch-Move-s32-folder-under-mach-s32.patch \
    file://bsp33/rc1/0016-arch-Rename-ARCH_S32-to-MACH_S32.patch \
    file://bsp33/rc1/0017-arch-Rename-NXP_S32G2XX-to-ARCH_S32G2.patch \
    file://bsp33/rc1/0018-arch-Rename-NXP_S32G3XX-to-ARCH_S32G3.patch \
    file://bsp33/rc1/0019-arch-Rename-NXP_S32R45-to-ARCH_S32R45.patch \
    file://bsp33/rc1/0020-arch-Rename-S32_GEN1-to-NXP_S32_CC-config.patch \
    file://bsp33/rc1/0021-include-dt-bindigns-Rename-S32_GEN1-to-NXP_S32_CC-co.patch \
    file://bsp33/rc1/0022-arch-Rename-asm-arch-s32-s32-gen1-to-asm-arch-s32-s3.patch \
    file://bsp33/rc1/0023-board-Rename-s32-gen1-board-to-s32-cc.patch \
    file://bsp33/rc1/0024-board-nxp-Move-SYS_VENDOR-under-arch-arm-mach-s32.patch \
    file://bsp33/rc1/0025-arch-Rename-S32GEN1_MAX_DTB_SIZE-to-S32_CC_MAX_DTB_S.patch \
    file://bsp33/rc1/0026-board-Create-configuration-for-each-S32-board.patch \
    file://bsp33/rc1/0027-arch-arm-mach-s32-Use-fdtdec_-to-initialize-DDR-bank.patch \
    file://bsp33/rc1/0028-arch-arm-mach-s32-Use-IS_ENABLED-to-guard-SAF1508-co.patch \
    file://bsp33/rc1/0029-arch-Make-SAF1508-initialization-part-of-S32GEVB-ini.patch \
    file://bsp33/rc1/0030-arch-mach-s32-Make-board-initialization-part-of-boar.patch \
    file://bsp33/rc1/0031-s32-serdes-hwconfig-Add-skip-option-to-hwconfig.patch \
    file://bsp33/rc1/0032-tools-Split-s32gen1image.h-header.patch \
    file://bsp33/rc1/0033-arch-arm-mach-s32-tools-Move-QSPI-tools-under-s32-cc.patch \
    file://bsp33/rc1/0034-tools-Rename-s32gen1image.c-to-s32ccimage.c.patch \
    file://bsp33/rc1/0035-tools-mkimage-Rename-S32GEN1-to-S32CC.patch \
    file://bsp33/rc1/0036-driver-crypto-Rename-HSE-firmware-file.patch \
    file://bsp33/rc1/0037-tools-mkimage-Correct-style-errors-from-tools-s32cci.patch \
    file://bsp33/rc1/0038-arch-arm-mach-s32-Remove-unused-MKIMAGE_T-config.patch \
    file://bsp33/rc1/0039-arch-s32-Rename-S32_CC-to-S32CC.patch \
    file://bsp33/rc1/0040-doc-device-tree-bindings-Move-s32cc-pinctrl-descript.patch \
    file://bsp33/rc1/0041-arch-arm-dts-Rename-fsl-s32-gen1-siul2-pinctrl-to-nx.patch \
    file://bsp33/rc1/0042-drivers-pinctrl-Rename-compatible-for-s32cc-driver.patch \
    file://bsp33/rc1/0043-drivers-pinctrl-nxp-Rename-s32-driver-to-s32cc.patch \
    file://bsp33/rc1/0044-arch-s32-cc-Remove-get_effective_memsize.patch \
    file://bsp33/rc1/0045-arch-s32-cc-Rename-cpu.c-to-soc.c.patch \
    file://bsp33/rc1/0046-board-nxp-s32cc-Sort-includes.patch \
    file://bsp33/rc1/0047-mkimage-s32ccimage-Sort-includes.patch \
    file://bsp33/rc1/0048-drivers-serial-linflex-Sort-includes.patch \
    file://bsp33/rc1/0049-pcie-Remove-no-check-serdes-dts-property.patch \
    file://bsp33/rc1/0050-pcie-doc-Remove-no-check-serdes-dts-property-from-do.patch \
    file://bsp33/rc1/0051-docs-gpio-use-s32cc-naming.patch \
    file://bsp33/rc1/0052-gpio-s32-change-compatible-naming-to-nxp-s32cc.patch \
    file://bsp33/rc1/0053-gpio-s32-use-s32cc-naming-for-the-gpio-driver.patch \
    file://bsp33/rc1/0054-s32g3xxaevb-select-MISC_INIT_R-if-SJA1105-y.patch \
    file://bsp33/rc1/0055-common-Drop-asm-global_data.h-from-common-header.patch \
    file://bsp33/rc1/0056-pcie-Set-PCI-device-ID-according-to-variant-bits.patch \
    file://bsp33/rc1/0057-mmc-fsl_esdhc_imx-Rename-s32gen1-to-s32cc.patch \
    file://bsp33/rc1/0058-arch-arm-dts-Rename-s32gen1-usdhc-compatible.patch \
    file://bsp33/rc1/0059-mmc-fsl_esdhc_imx-Use-esdhc_soc_data-flags-to-set-ho.patch \
    file://bsp33/rc1/0060-Revert-s32gen1-Disable-HS400-support-if-not-running-.patch \
    file://bsp33/rc1/0061-mmc-fsl_esdhc_imx-Revert-HS200-HS400-flags-interpret.patch \
    file://bsp33/rc1/0062-arch-s32-cc-Make-USDHC-options-mandatory.patch \
    file://bsp33/rc1/0063-nvram-siul2-Rename-s32gen1-to-s32cc.patch \
    file://bsp33/rc1/0064-arm-mach-s32-SCMI-agent-reset-cleanup.patch \
    file://bsp33/rc1/0065-cpu-Rename-s32gen1-to-s32cc.patch \
    file://bsp33/rc1/0066-configs-s32-Make-s32.h-content-part-of-s32-gen1.h.patch \
    file://bsp33/rc1/0067-configs-Rename-s32-gen1.h-to-s32-cc.h.patch \
    file://bsp33/rc1/0068-configs-s32-cc-Use-SZ_-macros-instead-of-raw-values.patch \
    file://bsp33/rc1/0069-configs-s32-cc-Embed-non-configurable-environment-op.patch \
    file://bsp33/rc1/0070-mach-s32-Rename-S32_-variables-to-S32CC_.patch \
    file://bsp33/rc1/0071-configs-Rename-s32g274a.h-to-s32g2.h.patch \
    file://bsp33/rc1/0072-configs-Move-target-settings-from-s32g2.h-into-targe.patch \
    file://bsp33/rc1/0073-configs-Rename-s32g399a.h-to-s32g3.h.patch \
    file://bsp33/rc1/0074-configs-Move-target-settings-from-s32r45.h-into-targ.patch \
    file://bsp33/rc1/0075-configs-Keep-S32CC-specific-options-in-s32-cc.h.patch \
    file://bsp33/rc1/0076-configs-Remove-custom-prompts-as-they-are-set-to-def.patch \
    file://bsp33/rc1/0077-configs-Remove-COUNTER_FREQUENCY-from-S32CC.patch \
    file://bsp33/rc1/0078-config_whitelist-Remove-CONFIG_DSPI_CS_CSK-and-CONFI.patch \
    file://bsp33/rc1/0079-fsl_dspi-Revert-DSPI_CTAR_PCSSCK_7CLK-to-original-va.patch \
    file://bsp33/rc1/0080-fsl_dspi-Correct-the-len-of-16-bit-framesize.patch \
    file://bsp33/rc1/0081-Revert-dspi-Add-support-for-8-and-16-bit-frame-size.patch \
    file://bsp33/rc1/0082-fsl_dspi-Use-wordlen-for-setting-16-bits-frame-size.patch \
    file://bsp33/rc1/0083-fsl_dspi-Fix-return-value-when-failing-to-retrieve-c.patch \
    file://bsp33/rc1/0084-u-boot-2020-Update-SAR-ADC-compatible-for-S32CC-plat.patch \
    file://bsp33/rc1/0085-s32-a53_gpr-Rename-compatible-string.patch \
    file://bsp33/rc1/0086-s32-cc-ddr-Remane-compatible-string.patch \
    file://bsp33/rc1/0087-timer-s32cc_pit-rename-s32-to-s32cc_pit.patch \
    file://bsp33/rc1/0088-dts-pit-rename-compatible-string.patch \
    file://bsp33/rc1/0089-s32-add-custom-implementation-for-soc_clk_dump.patch \
    file://bsp33/rc1/0090-i2c-mxc-revert-commit-s32-remove-s32-gen1-regs-heade.patch \
    file://bsp33/rc1/0091-s32-i2c-remove-unnecessary-code-from-mxc_i2c.c.patch \
    file://bsp33/rc1/0092-Kconfig-s32-cc-Add-board-help-information.patch \
    file://bsp33/rc1/0093-Kconfig-s32g2xxaevb-Add-board-help-information.patch \
    file://bsp33/rc1/0094-s32cc-Remove-double-ft_fixup_cpu-call.patch \
    file://bsp33/rc1/0095-pmic-vr5510-rename-compatible-string.patch \
    file://bsp33/rc1/0096-pmic-vr5510-fix-Coverity-issues.patch \
    file://bsp33/rc1/0097-s32g-use-DM_PMIC-only-for-s32g-evb-rdb-platforms.patch \
    file://bsp33/rc1/0098-pmic-pf5020-add-nxp-name-and-relocate-config.patch \
    file://bsp33/rc1/0099-pmic-fs5600-add-nxp-name-and-relocate-config.patch \
    file://bsp33/rc1/0100-pmic-pf5020-reorder-includes-checkIncludes-tests.patch \
    file://bsp33/rc1/0101-misc-s32cc_ocotp-rename-s32gen1-to-s32cc.patch \
    file://bsp33/rc1/0102-dts-ocotp-rename-compatible-string.patch \
    file://bsp33/rc1/0103-s32cc-Make-memory-fixups-part-of-ft_system_setup-cal.patch \
    file://0001-Make-s32g274ardb2-and-s32g2xxaevb-support-ostree.patch \
    file://0001-scripts-mailmapper-python2-python3.patch \
    file://0001-Makefile-add-.cfgout-file-dependency-to-fix-atf-buil.patch \
    file://0001-tools-s32gen1_secboot-replace-u-boot.s32-with-u-boot.patch \
    file://0001-tools-s32ccimage-add-reserved-member-for-struct-prog.patch \
    file://0001-u-boot-s32-Makefile-add-scripts_basic-dependency-to-.patch \
    ${@bb.utils.contains('HSE_SEC_ENABLED', '1', 'file://0001-configs-s32g2xxaevb-add-HSE_SECBOOT-config.patch', '', d)} \
"

SRCREV = "7cc85e188554fb38b6bd39a98b6149b033ebd53e"
SRC_URI[sha256sum] = "4e80caf195787c76639675712492230d090fe2eb435fd44491d653463485e30c"

SCMVERSION = "y"
LOCALVERSION = ""
PACKAGE_ARCH = "${MACHINE_ARCH}"
UBOOT_INITIAL_ENV = ""

USRC ?= ""
S = '${@oe.utils.conditional("USRC", "", "${WORKDIR}/git", "${USRC}", d)}'


# For now, only rdb2 boards support ATF, this function will be fixed when new ATF supported boards added.
do_install:append() {

    unset i j
    install -d ${DEPLOY_DIR_IMAGE}
    for config in ${UBOOT_MACHINE}; do
        i=$(expr $i + 1);
        for type in ${UBOOT_CONFIG}; do
            j=$(expr $j + 1)
            if  [ $j -eq $i ]; then
		if [ "${type}" = "s32g2xxaevb" ] && [ "${HSE_SEC_ENABLED}" = "1" ]; then
                        sed -i 's|${HSE_FW_DEFAULT_NAME}|${HSE_LOCAL_FIRMWARE_DIR}/${HSE_FW_NAME_S32G2}|g' ${B}/${config}/${UBOOT_CFGOUT}
		fi

                install -d ${DEPLOY_DIR_IMAGE}/${type}/tools
                cp ${B}/${config}/${UBOOT_BINARY} ${DEPLOY_DIR_IMAGE}/${type}/${UBOOT_BINARY}
                cp ${B}/${config}/tools/mkimage ${DEPLOY_DIR_IMAGE}/${type}/tools/mkimage
                cp ${B}/${config}/${UBOOT_CFGOUT} ${DEPLOY_DIR_IMAGE}/${type}/tools/${UBOOT_CFGOUT}

            fi
        done
        unset j
    done
    unset i
}


COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
