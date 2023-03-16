#
# Copyright (C) 2020 Wind River Systems, Inc.
#

COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
TARGET_SUPPORTED_KTYPES:nxp-s32g = "standard preempt-rt"

LINUX_BSP36_CD_RELEASE_PATCHES = " \
    file://bsp36/0001-gpio-s32cc-Add-get_direction-callback.patch \
    file://bsp36/0002-s32cc-qspi-Set-serial-flash-memory-address-mapping-t.patch \
    file://bsp36/0003-arm64-boot-dts-Add-device-trees-for-s32g2xxa-evb3-bo.patch \
    file://bsp36/0004-s32cc-dt-bindings-Update-TMU-compatible-strings.patch \
    file://bsp36/0005-s32cc-scmi-Define-SCMI-Clocks-for-TMU.patch \
    file://bsp36/0006-s32cc-thermal-Use-TMU-SCMI-clock.patch \
    file://bsp36/0007-s32cc-tmu-Update-TMU-Calibration-Table-for-S32CC-pla.patch \
    file://bsp36/0008-include-trace-Add-new-scmi_xfer_response_wait-event.patch \
    file://bsp36/0009-firmware-arm_scmi-Add-optee-transport.patch \
    file://bsp36/0010-firmware-arm_scmi-Perform-earlier-cinfo-lookup-call-.patch \
    file://bsp36/0011-firmware-arm_scmi-Set-polling-timeout-to-max_rx_time.patch \
    file://bsp36/0012-firmware-arm_scmi-Refactor-message-response-path.patch \
    file://bsp36/0013-firmware-arm_scmi-Use-new-trace-event-scmi_xfer_resp.patch \
    file://bsp36/0014-firmware-arm_scmi-Add-configurable-polling-mode-for-.patch \
    file://bsp36/0015-firmware-arm_scmi-Add-sync_cmds_completed_on_ret-tra.patch \
    file://bsp36/0016-firmware-arm_scmi-Add-support-for-atomic-transports.patch \
    file://bsp36/0017-firmware-arm_scmi-Make-smc-transport-use-common-comp.patch \
    file://bsp36/0018-firmware-arm_scmi-Make-smc-support-sync_cmds_complet.patch \
    file://bsp36/0019-firmware-arm_scmi-Add-atomic-mode-support-to-smc-tra.patch \
    file://bsp36/0020-firmware-arm_scmi-Split-channel-and-device-initializ.patch \
    file://bsp36/0021-firmware-arm_scmi-Add-notifications-to-SMC-transport.patch \
    file://bsp36/0022-firmware-arm_scmi-Add-a-method-to-verify-mailbox-s-s.patch \
    file://bsp36/0023-firmware-arm_scmi-Avoid-deadlock-when-called-with-di.patch \
    file://bsp36/0024-s32cc-tmu-Update-clks-dts-entry.patch \
    file://bsp36/0025-doc-s32cc-yaml-Fix-tmu-yaml-dts-example-syntax.patch \
"

LINUX_BSP36_RC5_PATCHES = " \
    file://bsp36/rc5/0027-arch-arm64-Enable-mmio-mux-for-S32CC-platforms.patch \
    file://bsp36/rc5/0028-dt-bindings-mux-s32cc-Add-IDs-for-CAN-timestamping.patch \
    file://bsp36/rc5/0029-dts-freescale-Add-nodes-for-SRC-module-found-on-S32C.patch \
    file://bsp36/rc5/0030-clocksource-stm-Replace-pr_err-with-dev_err.patch \
    file://bsp36/rc5/0031-clocksource-stm-Use-devm-interface-where-possible.patch \
    file://bsp36/rc5/0032-devicetree-bindings-Document-STM-compatible.patch \
    file://bsp36/rc5/0033-devicetree-bindings-stm-Add-a-property-for-clock-pre.patch \
    file://bsp36/rc5/0034-clocksource-stm-Add-a-new-compatilbe-for-timestampin.patch \
    file://bsp36/rc5/0035-clocksource-stm-Add-support-for-clock-prescaler.patch \
    file://bsp36/rc5/0036-dts-freescale-s32cc-Set-prescaler-to-max-value-for-S.patch \
    file://bsp36/rc5/0037-dts-freescale-s32cc-Make-STM7-a-timestamping-counter.patch \
    file://bsp36/rc5/0038-mailbox-llce-Mark-all-LLCE-packages-as-long-messages.patch \
    file://bsp36/rc5/0039-net-can-llce-Enable-TX-timestamping-by-default.patch \
    file://bsp36/rc5/0040-arch-arm64-Enable-STM-driver-for-S32CC-platforms.patch \
    file://bsp36/rc5/0041-dt-bindings-Add-TMU-Fuse-NVMEM-cell-offset-and-size.patch \
    file://bsp36/rc5/0042-s32cc-ocotp-Add-ocotp-driver.patch \
    file://bsp36/rc5/0043-doc-s32cc-ocotp-Add-S32CC-OCOTP-driver-dt-bindings-d.patch \
    file://bsp36/rc5/0044-s32cc-tmu-Read-fuse-memory-pages-using-NVRAM.patch \
    file://bsp36/rc5/0045-doc-bindings-tmu-Update-S32CC-TMU-devicetree-binding.patch \
    file://bsp36/rc5/0046-gpio-s32cc-Avoid-signal-glitch-on-set_direction.patch \
    file://bsp36/rc5/0047-firmware-scmi-Add-suspend-and-resume-callbacks-to-SC.patch \
    file://bsp36/rc5/0048-arm64-dts-s32g-Add-GPIO-SCMI-protocol-to-SCMI-node.patch \
    file://bsp36/rc5/0049-arm64-dts-s32cc-Add-channel-for-SCMI-notifications.patch \
    file://bsp36/rc5/0050-bindings-gpio-Add-compatible-for-S32CC-GPIO.patch \
    file://bsp36/rc5/0051-arm64-dts-Add-S32CC-compatible-to-GPIO-nodes.patch \
    file://bsp36/rc5/0052-firmware-arm_scmi-add-initial-support-for-gpio-proto.patch \
    file://bsp36/rc5/0053-gpio-add-support-for-GPIOs-provided-by-SCMI.patch \
    file://bsp36/rc5/0054-s32g3-rdb3-i2c4-Add-INA231-current-sensor-support.patch \
    file://bsp36/rc5/0055-doc-s32cc-yaml-Add-snps-ext_sys_time-for-setting-ext.patch \
    file://bsp36/rc5/0056-net-stmmac-Add-optional-setting-of-external-systime.patch \
    file://bsp36/rc5/0057-net-stmmac-Don-t-allow-systime-modifications-for-ext.patch \
    file://bsp36/rc5/0058-arm64-dts-s32g-pfe-remove-unused-global-property-nxp.patch \
    file://bsp36/rc5/0059-arm64-dts-s32g-pfe-extend-enum-for-nxp-pfeng-linked-.patch \
    file://bsp36/rc5/0060-arm64-dts-s32g-pfe-introduce-nxp-pfeng-linked-phyif-.patch \
    file://bsp36/rc5/0061-arm64-dts-s32g-pfe-add-remote-HIF-netdevs.patch \
    file://bsp36/rc5/0062-arm64-dts-s32g-Fix-S32GRV-VNP-PLAT-board-name.patch \
    file://bsp36/rc5/0063-dt-bindings-net-pfe-update-dt-bindings-documentation.patch \
	file://bsp36/rc5/nxp-s32g.cfg \
"

FILESEXTRAPATHS:prepend:nxp-s32g := "${THISDIR}/files:"
SRC_URI:append:nxp-s32g = " \
    file://0001-s32cc-update-to-align-with-bsp36-v5.15-kernel.patch \
    file://0002-xpcs-s32-rename-fsl-s32gen1-xpcs.h-to-nxp-s32cc-xpcs.patch \
    file://0003-xpcs-s32-rename-functions-s32gen1_xxx-to-s32cc_xxx-i.patch \
    file://0001-Revert-mmc-sdhci-esdhc-imx-Propagate-ESDHC_FLAG_HS40.patch \
    ${LINUX_BSP36_CD_RELEASE_PATCHES} \
    ${LINUX_BSP36_RC5_PATCHES} \
"
