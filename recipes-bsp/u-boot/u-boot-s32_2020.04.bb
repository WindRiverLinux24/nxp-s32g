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
    file://bsp34/rc1/0001-net-s32cc-gmac-remove-initialization-of-unused-ts-cl.patch \
    file://bsp34/rc1/0002-dm-core-add-non-translating-version-of-ofnode_get_ad.patch \
    file://bsp34/rc1/0003-mtd-add-support-for-parsing-partitions-defined-in-OF.patch \
    file://bsp34/rc1/0004-mtd-spi-nor-fill-in-mtd-dev-member.patch \
    file://bsp34/rc1/0005-mtd-remove-mtd_probe-function.patch \
    file://bsp34/rc1/0006-mtd-probe-SPI-NOR-devices-in-mtd_probe_devices.patch \
    file://bsp34/rc1/0007-mtd-cfi_mtd-populate-mtd-dev-with-flash_info-dev.patch \
    file://bsp34/rc1/0008-mtd-mtdpart-Make-mtdpart-s-_erase-method-sane.patch \
    file://bsp34/rc1/0009-s32cc-qspi-Add-MTD-partitions-support.patch \
    file://bsp34/rc1/0010-s32cc-flashboot-Update-Flash-Boot-by-use-of-mtd-inst.patch \
    file://bsp34/rc1/0011-qspi-env-Remove-obsolete-flash-environment-offsets.patch \
    file://bsp34/rc1/0012-dm-core-add-ofnode_get_path.patch \
    file://bsp34/rc1/0013-net-introduce-helpers-to-get-PHY-ofnode-from-MAC.patch \
    file://bsp34/rc1/0014-dm-add-cells_count-parameter-in-live-DT-APIs-of_pars.patch \
    file://bsp34/rc1/0015-pci-s32cc-Remove-the-usage-of-device_id-property.patch \
    file://bsp34/rc1/0016-serial-linflex-Avoid-devfdt_get_addr.patch \
    file://bsp34/rc1/0017-firmware-scmi-Avoid-fdtdec-library.patch \
    file://bsp34/rc1/0018-s32cc-nvmem-Use-ofnode-API-instead-of-fdtdec-calls.patch \
    file://bsp34/rc1/0019-pcie-serdes-Use-dev-interface-instead-of-fdtdec.patch \
    file://bsp34/rc1/0020-pcie-s32cc-Use-dev-interface-instead-of-fdtdec.patch \
    file://bsp34/rc1/0021-arm-s32cc-Replace-fdtdec-calls-with-ofnode-API.patch \
    file://bsp34/rc1/0022-adc-s32cc-Replace-devfdt_get_addr-with-dev_read_addr.patch \
    file://bsp34/rc1/0023-gpio-s32cc-Use-dev_read_addr-instead-of-devfdt_get_a.patch \
    file://bsp34/rc1/0024-pinctrl-s32cc-Use-dev_read_addr-instead-of-devfdt_ge.patch \
    file://bsp34/rc1/0025-firmware-psci-Replace-fdt_stringlist_get-with-ofnode.patch \
    file://bsp34/rc1/0026-net-gmac-Replace-fdt-calls-with-dev-and-ofnode-APIs.patch \
    file://bsp34/rc1/0027-net-pfe-Replace-fdt-calls-with-dev-and-ofnode-APIs.patch \
    file://bsp34/rc1/0028-misc-sja1105-Replace-fdt-calls-with-ofnode-alternati.patch \
    file://bsp34/rc1/0029-usb-mx6-Replace-devfdt_get_addr-with-dev_read_addr.patch \
    file://bsp34/rc1/0030-spi-fsl_qspi-Replace-fdtdec-calls-with-dev-API.patch \
    file://bsp34/rc1/0031-mmc-fsl_imx-Replace-fdt-calls-with-dev-equivalents.patch \
    file://bsp34/rc1/0032-spi-fsl_dspi-Use-dev-API-instead-of-fdtdec-calls.patch \
    file://bsp34/rc1/0033-i2c-mxc-Replace-fdt-calls-with-dev-equivalents.patch \
    file://bsp34/rc8/0001-pcie-Add-compatible-for-endpoint.patch \
    file://bsp34/rc8/0002-arm-s32cc-Make-PCI-devices-initialization-part-of-pl.patch \
    file://bsp34/rc8/0003-arm-s32cc-Make-serdes_hwconfig.h-a-platform-header.patch \
    file://bsp34/rc8/0004-arm-s32cc-Set-SERDES_INVALID-on-0-instead-of-1.patch \
    file://bsp34/rc8/0005-s32cc-Update-SerDes-and-PCIe-nodes-based-on-hwconfig.patch \
    file://bsp34/rc8/0006-serial-linflex-Add-terminating-entry-to-compatible-l.patch \
    file://bsp34/rc8/0007-i2c-mxc-move-clk-divisors-to-driver-data.patch \
    file://bsp34/rc8/0008-i2c-mxc-add-clock-divisors-for-S32CC-SoCs.patch \
    file://bsp34/rc8/0009-i2c-mxc-sort-include-files.patch \
    file://bsp34/rc8/0010-s32cc-fdt-apply-lockstep-cpu-fixup-for-u-boot-dtb.patch \
    file://bsp34/rc8/0011-s32cc-mp-get-cpu-by-index.patch \
    file://bsp34/rc8/0012-s32g3-qspi-Update-SMPR-DLLFSMPF-setting-for-DTR-OPI-.patch \
    file://bsp34/rc8/0013-pinctrl-s32-enable-driver-after-SIUL2-unification.patch \
    file://bsp34/rc8/0014-pinctrl-add-pinctrl_gpio_set_config.patch \
    file://bsp34/rc8/0015-pinctrl-s32cc-implement-more-pinctrl_ops.patch \
    file://bsp34/rc8/0016-gpio-s32-enable-driver-after-dts-unification.patch \
    file://bsp34/rc8/0017-s32-nvmem-change-cell-address-parsing.patch \
    file://bsp34/rc8/0018-misc-s32-change-reg-address-parsing.patch \
    file://bsp34/rc8/0019-configs-s32g-pfe-sync-default-port-configs-with-linu.patch \
    file://bsp34/rc8/0020-drivers-net-s32-cc-minimize-eth-fixup.patch \
    file://bsp34/rc8/0021-drivers-net-s32-cc-fix-eth-fixup-for-RDB2-rev.D.patch \
    file://bsp34/rc8/0022-arm-s32cc-Make-SerDes-hwconfig-handling-part-of-the-.patch \
    file://bsp34/rc8/0023-arm-s32cc-Correct-the-validation-of-the-length-retur.patch \
    file://bsp34/rc8/0024-drivers-reset-Add-a-managed-API-to-get-reset-control.patch \
    file://bsp34/rc8/0025-reset-add-reset-controller-driver-for-SCMI-agents.patch \
    file://bsp34/rc8/0026-pci-serdes-Use-reset-controller-instead-of-accessing.patch \
    file://bsp34/rc8/0027-pci-Move-PCI-specific-code-from-SerDes-driver.patch \
    file://bsp34/rc8/0028-pci-Remove-the-list-of-SerDes-instances.patch \
    file://bsp34/rc8/0029-pcie-s32cc-Remove-PCIe-mode-reporting-from-SerDes-dr.patch \
    file://bsp34/rc8/0030-arm-s32cc-Make-hwconfig-validation-part-of-the-platf.patch \
    file://bsp34/rc8/0031-pcie-Remove-the-set-of-pipeP_pclk-from-S32CC-SerDes-.patch \
    file://bsp34/rc8/0032-arm-s32cc-Make-SERDES_SKIP-interpretation-part-of-hw.patch \
    file://bsp34/rc8/0033-arm-s32cc-Make-SerDes-mode5-validations-part-of-the-.patch \
    file://bsp34/rc8/0034-arm-s32cc-Make-SerDes-phy-mode-validation-part-of-th.patch \
    file://bsp34/rc8/0035-pci-s32cc-SerDes-header-cleanup.patch \
    file://bsp34/rc8/0036-pci-s32cc-Replace-wait_read32-with-readl_poll_timeou.patch \
    file://bsp34/rc8/0037-pcie-serdes-Split-SerDes-control-between-PCIe-phy-an.patch \
    file://bsp34/rc8/0038-pci-serdes-Use-the-same-binding-for-registers-as-in-.patch \
    file://bsp34/rc8/0039-pci-serdes-Use-the-same-bindings-for-clocks-as-in-Li.patch \
    file://bsp34/rc8/0040-pci-serdes-Replace-enum-serdes_clock-with-a-bool.patch \
    file://bsp34/rc8/0041-pci-serdes-Replace-serdes_clock_fmhz-with-the-clock-.patch \
    file://bsp34/rc8/0042-pci-s32cc-Make-SerDes-mode-validation-part-of-platfo.patch \
    file://bsp34/rc8/0043-pci-s32cc-Read-SerDes-mode-from-device-tree.patch \
    file://bsp34/rc8/0044-hse-add-shared-secret-key-group-to-ram-catalog.patch \
    file://bsp34/rc8/0045-dt-bindings-pinctrl-rename-s32-gen1-pinctrl.h.patch \
    file://bsp34/rc8/0046-gpio-s32-rename-include.patch \
    file://bsp34/rc8/0047-dt-bindings-pinctrl-s32cc-remove-s32cc-pinctrl.h.patch \
    file://bsp34/rc8/0048-gpio-s32cc-remove-reference-to-s32cc-pinctrl.h.patch \
    file://bsp34/rc8/0049-pinctrl-s32-add-support-for-SRE-configuration.patch \
    file://bsp34/rc8/0050-pinctrl-s32-add-list-of-supported-pinconf_params.patch \
    file://bsp34/rc8/0051-test-fdtdec-test-fdtdec_set_carveout.patch \
    file://bsp34/rc8/0052-dm-core-add-ofnode-and-dev-function-to-iterate-on-no.patch \
    file://bsp34/rc8/0053-pinctrl-s32-update-s32_set_state.patch \
    file://bsp34/rc8/0054-arm-s32cc-Get-a-reference-to-PCIe-node-before-enabli.patch \
    file://bsp34/rc8/0055-dt-bindings-Sync-include-dt-bindings-phy-phy.h-from-.patch \
    file://bsp34/rc8/0056-generic-phy-add-configure-op.patch \
    file://bsp34/rc8/0057-generic-phy-add-set_mode-op.patch \
    file://bsp34/rc8/0058-pci-Use-nvmem-cells-instead-a-link-to-SIUL2-nvmem-dr.patch \
    file://bsp34/rc8/0059-pcie-Replace-serdes_phy_mode-with-pcie_phy_mode.patch \
    file://bsp34/rc8/0060-pci-serdes-Isolate-PCIe-phy-initialization.patch \
    file://bsp34/rc8/0061-pci-Make-SerDes-driver-a-PHY-driver.patch \
    file://bsp34/rc8/0062-pci-Initialize-PCIE-phy-through-generic-PHY-API.patch \
    file://bsp34/rc8/0063-ethtool-Add-macros-for-100baseT1_Full-and-2500baseT1.patch \
    file://bsp34/rc8/0064-generic-phy-Include-header-dependencies.patch \
    file://bsp34/rc8/0065-regmap-Include-ofnode-header.patch \
    file://bsp34/rc8/0066-s32cc-Add-XPCS-driver.patch \
    file://bsp34/rc8/0067-net-pfeng-Initialize-XPCS-using-generic-phy-API.patch \
    file://bsp34/rc8/0068-net-gmac-s32-Initialize-XPCS-using-generic-phy-API.patch \
    file://bsp34/rc8/0069-s32cc-Add-fixup-for-SerDes-demo-mode5.patch \
    file://bsp34/rc8/0070-net-xpcs-Add-support-for-demo-mode5.patch \
    file://bsp34/rc8/0071-net-Make-xpcs-command-part-of-the-XPCS-driver.patch \
    file://bsp34/rc8/0072-s32cc-Remove-unused-SerDes-files.patch \
    file://bsp34/rc8/0073-s32cc-Remove-platform-SerDes-initialization.patch \
    file://bsp34/rc8/0074-s32-env-Update-fdt_enable_hs400es.patch \
    file://bsp34/rc8/0075-net-phy-fixed-Be-compatible-with-live-OF-tree.patch \
    file://bsp34/rc8/0076-net-pcs-Add-small-description-for-XPCS-driver.patch \
    file://bsp34/rc8/0077-net-xpcs-Add-comments-for-hard-coded-values.patch \
    file://bsp34/rc8/0078-phy-s32cc-serdes-Use-one-line-comment-to-document-st.patch \
    file://bsp34/rc8/0079-net-dwc-s32cc-Use-dev_err-for-errors-instead-of-prin.patch \
    file://bsp34/rc8/0080-net-pfeng-Use-dev_err-for-errors-instead-of-printf.patch \
    file://bsp34/rc8/0081-include-xpcs-Correct-typos.patch \
    file://bsp34/rc8/0082-net-pcs-s32cc-xpcs-Use-u32-instead-of-unsigned-int.patch \
    file://bsp34/rc8/0083-s32g3-hwconfig-Update-xpcs_mode-for-S32G3-EVB-EVB3-p.patch \
    file://bsp34/rc8/0084-arm-s32-Remove-cluster1-when-running-in-lokstep.patch \
    file://bsp34/rc8/0085-rdb-rev_id-Correct-return-code-check.patch \
    file://bsp34/rc8/0086-qspi-Avoid-use-of-uninitialized-value.patch \
    file://bsp34/rc8/0087-misc-s32cc_cmu-Include-FIRC-drift-in-Expected-range.patch \
    file://bsp34/rc8/0088-dt-bindings-pinctrl-update-documentation-for-SIUL2-p.patch \
    file://bsp34/rc8/0089-dt-bindings-gpio-update-SIUL2-gpio-bindings.patch \
    file://bsp34/rc8/0090-fdt-pcie-Do-not-skip-fdt-fixups-for-Linux.patch \
    file://bsp34/rc8/0091-s32cc-pcie-Update-width-configuration.patch \
    file://bsp34/rc8/0092-s32cc-serdes-Rename-a-few-functions-to-follow-naming.patch \
    file://bsp34/rc8/0093-s32cc-pcie-Limit-speed-to-Gen2-in-mode5.patch \
    file://bsp34/rc8/0094-s32cc-pcie-Remove-some-duplicate-code-and-regroup-so.patch \
    file://bsp34/rc8/0095-s32cc-pcie-Limit-PCIe-width-and-speed-for-combo-mode.patch \
    file://bsp34/rc8/0096-arm-s32-cc-Increase-max-DTB-size-to-0x8000.patch \
    file://bsp34/rc8/0097-s32-hse-refactor-RAM-Key-catalog.patch \
    file://bsp34/rc8/0098-net-pfeng-initialize-ret-variable.patch \
    file://bsp34/rc8/0099-net-pfeng-handle-memory-leaks.patch \
    file://bsp34/rc8/0100-net-pfeng-handle-memory-leak.patch \
    file://bsp34/rc8/0101-s32cc-pcie-Fix-EP-mode-initialization.patch \
    file://bsp34/rc8/0102-s32g-hwconfig-Unify-xpcs_mode-for-S32G.patch \
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
    plats="s32g2xxaevb s32g274ardb2 s32g399ardb3 s32g3xxaevb"
    if [ "${HSE_SEC_ENABLED}" = "1" ]; then
        for plat in $plats; do
            cfgout="${B}/${plat}_defconfig/u-boot-s32.cfgout"
            if [ -e $cfgout ]; then
                if [ $plat = "s32g2xxaevb" ] || [ $plat = "s32g274ardb2" ]; then
                    sed -i 's|${HSE_FW_DEFAULT_NAME}|${HSE_LOCAL_FIRMWARE_DIR}/${HSE_FW_NAME_S32G2}|g' $cfgout
                else
                    sed -i 's|${HSE_FW_DEFAULT_NAME}|${HSE_LOCAL_FIRMWARE_DIR}/${HSE_FW_NAME_S32G3}|g' $cfgout
                fi
            fi
        done
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
