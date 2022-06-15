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
    file://bsp33/rc2/0001-s32cc-Make-memory-fixups-part-of-ft_system_setup-cal.patch \
    file://bsp33/rc2/0002-s32cc-Rename-fsl-s32gen1-to-nxp-s32cc.patch \
    file://bsp33/rc2/0003-s32cc-Rename-fsl-s32cc-dwmac-to-nxp-s32cc-dwmac.patch \
    file://bsp33/rc2/0004-s32cc-Rename-fsl-s32gen1-pcie-to-nxp-s32cc-pcie.patch \
    file://bsp33/rc2/0005-dt-bindings-pcie-Rename-fsl-s32gen1-pcie-to-nxp-s32c.patch \
    file://bsp33/rc2/0006-dts-Rename-fsl-s32g-to-nxp-s32g.patch \
    file://bsp33/rc2/0007-s32cc-Rename-fsl-s32gen1-serdes-to-nxp-s32cc-serdes.patch \
    file://bsp33/rc2/0008-s32cc-Rename-fsl-s32g399-to-nxp-s32g399.patch \
    file://bsp33/rc2/0009-s32cc-Rename-fsl-s32g274-to-nxp-s32g274.patch \
    file://bsp33/rc2/0010-s32cc-Rename-fsl-s32r45-to-nxp-s32r45.patch \
    file://bsp33/rc2/0011-s32cc-Rename-fsl-s32cc-usdhc-to-nxp-s32cc-usdhc.patch \
    file://bsp33/rc2/0012-driver-spi-fsl_qspi-Rename-s32gen1-to-s32cc.patch \
    file://bsp33/rc2/0013-arm-dts-s32-gen1-Set-max-frequency-for-QSPI-controll.patch \
    file://bsp33/rc2/0014-mach-s32-soc.c-cleanup.patch \
    file://bsp33/rc2/0015-s32-cc-qspi-Use-new-compatible-strings-for-s32g-and-.patch \
    file://bsp33/rc2/0016-Move-QSPI-and-MMC-settings-in-Kconfig.patch \
    file://bsp33/rc2/0017-Move-DEFAULT_DEVICE_TREE-in-Kconfig.patch \
    file://bsp33/rc2/0018-s32g-Make-SPI_FLASH_MACRONIX-S32G-EVB-and-S32G-RDB-d.patch \
    file://bsp33/rc2/0019-s32-cc-Remove-unused-ENV_FLASH_ADDR.patch \
    file://bsp33/rc2/0020-s32-cc-Remove-unused-_SYS_MAX_FLASH_-variables.patch \
    file://bsp33/rc2/0021-s32gen1-Enable-support-for-MMC-DDR52.patch \
    file://bsp33/rc2/0022-s32gen1-Move-no-1-8-v-to-the-board-.dts.patch \
    file://bsp33/rc2/0023-dts-Remove-fsl-prefix-from-S32CC-device-trees.patch \
    file://bsp33/rc2/0024-dts-Rename-s32-gen1.dtsi-to-s32-cc.dtsi.patch \
    file://bsp33/rc2/0025-s32-cc-Disable-FDT-and-initrd-relocation.patch \
    file://bsp33/rc2/0026-s32cc-Minimize-the-size-of-defconfigs.patch \
    file://bsp33/rc2/0027-s32-change-clk-dump-header.patch \
    file://bsp33/rc2/0028-s32cc-Add-distinct-support-for-S32G2-S32G3-and-EVB-E.patch \
    file://bsp33/rc2/0029-s32g-evb-Use-specific-S32G2-S32G3-EVB-configs.patch \
    file://bsp33/rc2/0030-s32g3-rdb3-Remove-enablement-duplication-of-ADC.patch \
    file://bsp33/rc2/0031-gmac-s32-Fixed-GMAC-clock-failed-message-in-U-Boot-l.patch \
    file://bsp33/rc2/0032-phy-nxp-c45-tja11xx-sort-include-files.patch \
    file://bsp33/rc2/0033-phy-nxp-c45-tja11xx-check-for-FUSA_PASS-irq.patch \
    file://bsp33/rc3/0001-s32-cc-mp-fix-order-of-headers.patch \
    file://bsp33/rc3/0002-dts-s32cc-Rename-s32g274a.dtsi-to-s32g.dtsi.patch \
    file://bsp33/rc3/0003-s32cc-Fix-S32R45-EMU-compilation.patch \
    file://bsp33/rc3/0004-dts-s32cc-Update-root-node-compatible-to-reflect-SoC.patch \
    file://bsp33/rc3/0005-dts-s32cc-Remove-unused-s32g274a-sec-boot.dts.patch \
    file://bsp33/rc3/0006-arm-dts-Add-spi-num-chipselects-property-to-SPI-node.patch \
    file://bsp33/rc3/0007-dt-bindings-nvmem-Add-SIUL2-offsets.patch \
    file://bsp33/rc3/0008-s32cc-Add-cells-to-SIUL2-NVRAM.patch \
    file://bsp33/rc3/0009-serial-Update-S32CC-LinFlex-compatible-to-nxp-s32cc-.patch \
    file://bsp33/rc3/0010-spi-Update-S32CC-DSPI-compatible-to-nxp-s32cc-dspi.patch \
    file://bsp33/rc3/0011-i2c-Update-S32CC-I2C-compatible-to-nxp-s32cc-i2c.patch \
    file://bsp33/rc3/0012-pfe-Fix-NULL-accesses-for-platform-memmap.patch \
    file://bsp33/rc3/0013-pfe-Drop-unused-header-files.patch \
    file://bsp33/rc3/0014-misc-s32cc_siul2_nvram-print-correct-revision-for-cp.patch \
    file://bsp33/rc3/0015-s32cc-emu-Increase-LinFlex-baudrate-to-max-value.patch \
    file://bsp33/rc3/0016-s32cc-emu-Increase-DDR-size-to-2GB.patch \
    file://bsp33/rc3/0017-arm-s32cc-Remove-unused-S32GEN1_DRAM_INLINE_ECC.patch \
    file://bsp33/rc3/0018-arm-s32cc-Use-the-same-.text-address-for-emulator-as.patch \
    file://bsp33/rc3/0019-arm-dts-Add-USDHC-pinmuxing-for-S32G3-emulator.patch \
    file://bsp33/rc3/0020-s32cc-Reduce-PIT-rate-to-increase-responsiveness-on-.patch \
    file://bsp33/rc3/0021-ddr-Remove-DDR-ERR050543-dts-fixup.patch \
    file://bsp33/rc3/0022-mach-s32-s32-cc-backport-startm7-changes-from-2022.0.patch \
    file://bsp33/rc3/0023-s32-cc-fix-linux-overlap-with-device-tree.patch \
    file://bsp33/rc3/0024-doc-s32cc-cmu-add-driver-bindings.patch \
    file://bsp33/rc3/0025-bindings-clock-s32cc-add-CMU-clk.patch \
    file://bsp33/rc3/0026-misc-s32cc-cmu-move-cmu-driver-to-DM.patch \
    file://bsp33/rc3/0027-misc-s32cc-cmu-change-double-data-type-to-u64.patch \
    file://bsp33/rc3/0028-s32-serdes-Replace-pciex-reference-to-serdesx-refere.patch \
    file://bsp33/rc3/0029-s32-serdes-pcie-Refactor-register-definitions.patch \
    file://bsp33/rc3/0030-checkpatch.pl-Update-to-v5.7.patch \
    file://bsp33/rc3/0031-dts-Add-a-dash-between-SoC-and-board-name.patch \
    file://bsp33/rc3/0032-arm-dts-Specify-the-name-of-device-tree-for-S32R45-e.patch \
    file://bsp33/rc3/0033-dts-Rename-s32-cc.dtsi-to-s32cc.dtsi.patch \
    file://bsp33/rc3/0034-dts-Rename-s32gxxxaevb.dtsi-to-s32gxxxa-evb.dtsi.patch \
    file://bsp33/rc3/0035-dts-Rename-s32gxxxardb.dtsi-to-s32gxxxa-rdb.dtsi.patch \
    file://bsp33/rc3/0036-adc-Index-ADC-instances-by-alias-in-device-tree.patch \
    file://bsp33/rc3/0037-board-s32grdb-Get-ADC-instance-by-index.patch \
    file://bsp33/rc3/0038-arm-dts-s32cc-Reposition-reserved-memory-and-firmwar.patch \
    file://bsp33/rc3/0039-arm-s32cc-Get-A53GPR-driver-instance-based-on-node-o.patch \
    file://bsp33/rc3/0040-arm-dts-Make-a53gpr-part-of-the-soc-node.patch \
    file://bsp33/rc3/0041-arm-dts-s32cc-Make-CMU-part-of-the-soc-node.patch \
    file://bsp33/rc3/0042-arm-dts-s32cc-Make-QSPI-part-of-the-soc-node.patch \
    file://bsp33/rc3/0043-timer-s32cc-pit-Rename-compatible.patch \
    file://bsp33/rc3/0044-arm-dts-s32cc-Make-UART-instances-part-of-the-soc-no.patch \
    file://bsp33/rc3/0045-arm-dts-s32cc-Make-SPI-instances-part-of-the-soc-nod.patch \
    file://bsp33/rc3/0046-arm-dts-s32cc-Make-I2C-instances-part-of-the-soc-nod.patch \
    file://bsp33/rc3/0047-arm-dts-s32cc-Make-ADC-instances-part-of-the-soc-nod.patch \
    file://bsp33/rc3/0048-arm-dts-s32cc-Make-uSDHC-part-of-the-soc-node.patch \
    file://bsp33/rc3/0049-arm-dts-s32cc-Make-OCOTP-part-of-the-soc-node.patch \
    file://bsp33/rc3/0050-arm-dts-s32cc-Make-GMAC-part-of-the-soc-node.patch \
    file://bsp33/rc3/0051-arm-dts-s32cc-Make-PCIE-and-SerDes-instances-part-of.patch \
    file://bsp33/rc3/0052-arm-dts-s32cc-Make-GIC-part-of-the-soc-node.patch \
    file://bsp33/rc3/0053-arm-dts-s32g-Make-SIUL2-instances-part-of-the-soc-no.patch \
    file://bsp33/rc3/0054-arm-dts-s32g-Make-SerDes1-PCIe1-part-of-the-soc-node.patch \
    file://bsp33/rc3/0055-arm-dts-s32c-Make-PFE-part-of-the-soc-node.patch \
    file://bsp33/rc3/0056-arm-dts-s32r45-Place-common-S32R45-nodes-in-s32r45.d.patch \
    file://bsp33/rc3/0057-arm-dts-Add-a-device-tree-for-S32R45-emulator.patch \
    file://bsp33/rc3/0058-arm-dts-Add-a-device-tree-for-S32R45-simulator.patch \
    file://bsp33/rc3/0059-arm-dts-Add-device-tree-for-S32G274A-simulator.patch \
    file://bsp33/rc3/0060-arm-dts-s32g3-Make-GIC-part-of-the-soc-node.patch \
    file://bsp33/rc3/0061-arm-dts-Move-PCIe1-alias-to-S32CC-device-tree.patch \
    file://bsp33/rc3/0062-arm-dts-Make-PCIe1-and-SerDes1-part-of-S32CC-dtsi.patch \
    file://bsp33/rc3/0063-configs-s32cc-Use-the-same-names-for-U-Boot-and-Linu.patch \
    file://bsp33/rc3/0064-arm-dts-s32cc-Update-model-name-for-emulation-platfo.patch \
    file://bsp33/rc5/0001-dt-bindings-Add-definitions-for-reserved-ddr-errata-.patch \
    file://bsp33/rc5/0002-ddr-err050543-Sync-dtb.patch \
    file://bsp33/rc5/0003-arm-s32-cc-remove-cmu-header.patch \
    file://bsp33/rc5/0004-dts-r45-emu-Remove-the-removal-of-mem2-node.patch \
    file://bsp33/rc5/0005-pci-s32cc-Check-option-for-NULL-before-using-it.patch \
    file://bsp33/rc5/0006-s32-gen1-cpu-Add-nvmem-cell-properties-to-secondary-.patch \
    file://bsp33/rc5/0007-s32cc-add-ERR051257-erratum-workaround.patch \
    file://bsp33/rc5/0008-s32cc-mp-Fix-cpus-offset-retrieval.patch \
    file://bsp33/rc5/0009-s32cc-fdt-Simplify-cpu-fixup-logic-when-parsing-dtb.patch \
    file://bsp33/rc5/0010-arm-dts-s32cc-Remove-unused-clock-frequency-property.patch \
    file://bsp33/rc5/0011-arm-dts-s32cc-Remove-device_type-from-UART-nodes.patch \
    file://bsp33/rc5/0012-arm-dts-s32c-Add-SPI-properties-from-Linux.patch \
    file://bsp33/rc5/0013-arm-dts-s32cc-Don-t-reference-cpus-node-using-a-name.patch \
    file://bsp33/rc5/0014-arm-s32cc-Remove-binman-node.patch \
    file://bsp33/rc5/0015-s32cc-Remove-the-generation-of-u-boot-s32.bin.patch \
    file://bsp33/rc5/0016-arm-s32cc-Use-an-empty-device-tree.patch \
    file://bsp33/rc5/0017-s32cc-Move-u-boot-s32.cfgout-target-under-BUILD_TARG.patch \
    file://bsp33/rc5/0018-net-s32cc-gmac-Sync-bindings.patch \
    file://bsp33/rc5/0019-configs-s32cc-Increase-line-buffer-size.patch \
    file://bsp33/rc5/0020-board-s32cc-Enable-distro-boot-flow.patch \
    file://bsp33/rc5/0021-drivers-fsl_qspi-implement-.set_speed.patch \
    file://bsp33/rc5/0022-drivers-fsl_qspi-sort-include.patch \
    file://bsp33/rc5/0023-configs-s32cc-Pass-a-copy-of-DTB-to-distro-build.patch \
    file://bsp33/rc5/0024-serial-linflex-debug-uart-does-not-select-FSL_LINFLE.patch \
    file://bsp33/rc5/0025-s32cc-fdt-fix-cpu-fixup.patch \
    file://bsp33/rc5/0026-s32g-serdes-Add-option-to-configure-serdes-mode5.patch \
    file://bsp33/rc5/0027-s32g-serdes-Add-some-new-diagnostic-option-for-serde.patch \
    file://bsp33/rc5/0028-s32g-etherent-Allow-for-all-PFE-EMACs-to-run-at-2.5G.patch \
    file://bsp33/rc5/0030-secboot-add-support-for-reading-sig-from-fip.patch \
    file://0001-scripts-mailmapper-python2-python3.patch \
    file://0001-tools-s32gen1_secboot-replace-u-boot.s32-with-u-boot.patch \
    file://0001-tools-s32ccimage-add-reserved-member-for-struct-prog.patch \
    file://0001-u-boot-s32-Makefile-add-scripts_basic-dependency-to-.patch \
    file://0001-configs-Enable-commands-for-ostree.patch \
    file://0001-include-config_distro_bootcmd.h-Check-go-before-boot.patch \
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
