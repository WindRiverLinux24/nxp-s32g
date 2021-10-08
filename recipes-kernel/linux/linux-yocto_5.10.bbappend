require linux-yocto-nxp-s32g2xx.inc

KBRANCH:nxp-s32g2xx  = "v5.10/standard/nxp-sdk-5.10/nxp-s32g2xx"

LINUX_VERSION:nxp-s32g2xx ?= "5.10.x"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append:nxp-s32g2xx = " \
    file://bsp30/rc5/0001-Revert-hse-remove-uio-component-from-crypto-driver.patch \
    file://bsp30/rc6/0001-Revert-crypto-hse-remove-uio-implementation-artifact.patch \
    file://bsp31/rc1/0002-net-can-llce-Correct-the-way-the-logger-advertise-it.patch \
    file://bsp31/rc1/0003-i2c-imx-support-slave-mode-for-imx-I2C-driver.patch \
    file://bsp31/rc1/0004-dts-s32v234-hpcsom-add-device-tree-for-hpc-som.patch \
    file://bsp31/rc1/0005-s32-reorder-dtb-files-in-the-dts-makefile.patch \
    file://bsp31/rc1/0006-pcie-s32v234-Add-support-for-s32v234-pcie-driver.patch \
    file://bsp31/rc1/0007-pcie-dma-Update-PCIE_DMA_-configurations.patch \
    file://bsp31/rc1/0008-pcie-dma-Set-send_signal_to_user-and-fix-memory-corr.patch \
    file://bsp31/rc1/0009-pcie-s32gen1-Fix-dma-support.patch \
    file://bsp31/rc1/0010-pci-s32v234-enable-msi-capability-in-RC-mode.patch \
    file://bsp31/rc1/0011-s32-Make-selection-of-S32-SOC-mutually-exclusive.patch \
    file://bsp31/rc1/0012-s32-pcie-create-separate-header-file-for-ioctl-calls.patch \
    file://bsp31/rc1/0013-s32g274a-ddr-Fixed-read_lpddr4_mr-function.patch \
    file://bsp31/rc1/0014-gpio-siul2-s32gen1-Correct-IRQ-mapping.patch \
    file://bsp31/rc1/0015-gpio-siul-s32gen1-Share-eirq-regmap-among-siul2-inst.patch \
    file://bsp31/rc1/0016-s32gen1-usdhc-Perform-strobe-DLL-lock-at-200-MHz.patch \
    file://bsp31/rc1/0017-s32gen1-pcie-Probe-PCIe-host-controller-even-with-no.patch \
    file://bsp31/rc2/0001-crypto-hse-fix-incorrect-handling-of-firmware-state.patch \
    file://bsp31/rc2/0002-crypto-hse-wait-for-firmware-init-on-resume.patch \
    file://bsp31/rc2/0003-s32-adc-Correctly-check-bus-clock-frequency-based-on.patch \
    file://bsp31/rc2/0004-linux-s32-pcie-Fix-suspend-resume-crash-with-no-EP-p.patch \
    file://bsp31/rc2/0005-xen-extend-xen_swiotlb_dma_ops-with-map_resource-and.patch \
    file://bsp31/rc2/0006-uio-hse-standalone-user-space-driver-support.patch \
    file://bsp31/rc2/0007-dt-bindings-crypto-hse-add-dt-bindings-documentation.patch \
    file://bsp31/rc2/0013-dts-fsl-s32g274a-evb-add-sja1105-bindings.patch \
    file://bsp31/rc2/0014-linux-s32-pcie-Add-hot-unplug-support.patch \
    file://bsp31/rc2/0015-linux-s32-pcie-Fix-e1000e-crash-with-surprise-hot-un.patch \
    file://bsp31/rc2/0016-linux-s32-pcie-Add-Hot-Plug-support.patch \
    file://bsp31/rc2/0017-bindings-arm-nxp-Add-bindings-for-S32-boards.patch \
    file://bsp31/rc2/0018-dts-freescale-Add-device-tree-for-s32g3xxx-evb-board.patch \
    file://bsp31/rc2/0019-dts-fsl-s32-gen1-Add-nodes-STM-3-7.patch \
    file://bsp31/rc2/0020-dts-fsl-s32-gen1-Add-nodes-STM-8-11.patch \
    file://bsp31/rc2/reverted/0001-Revert-dt-bindings-crypto-hse-add-dt-bindings-docume.patch \
    file://bsp31/rc2/reverted/0002-Revert-uio-hse-standalone-user-space-driver-support.patch \
    file://bsp31/rc2/reverted/0003-Revert-crypto-hse-wait-for-firmware-init-on-resume.patch \
    file://bsp31/rc2/reverted/0004-Revert-crypto-hse-fix-incorrect-handling-of-firmware.patch \
    file://0001-arch-arm64-s32g-disable-virtio_block-dts-node-by-def.patch \ 
    file://0002-s32gen1-pcie-Remove-duplicate-interrupt-resource-req.patch \
    file://0003-driver-pci-pci-s32gen1-remove-the-__init-macro.patch \ 
    file://0006-dts-s32g27a-pfe-move-reserve-memory-address-to-leave.patch \ 
    file://0007-drivers-mailbox-replace-mutex-with-spinlock-in-llce_.patch \ 
    file://0008-arch-arm64-s32g-add-skew-ps-property-for-ksz9031-phy.patch \ 
    file://0009-dts-Add-phys-to-PFE-to-allow-usage-of-SerDes-driver.patch \ 
    file://0010-Documentation-pfe-bindings-of-SerDes-phys.patch \ 
    file://0011-documentation-fsl-pfeng-PFE-controller-reset-support.patch \ 
    file://0012-dt-bindings-s32g274a-pfe-PFE-controller-reset-suppor.patch \ 
    file://0013-arch-arm64-dts-keep-i2c1-in-disabled-status.patch \
    file://0014-drivers-phy-s32gen1-serdes-drop-the-redundant-phy-id.patch \
    file://0016-dts-s32g274a-rdb2-disable-ARQ107-phy-node-explicitly.patch \
    file://0001-drivers-llce-mailbox-delete-DO_ONCE-call-for-llce_ca.patch \
    file://0002-drivers-llce_can-put-echo-skb-before-sending-message.patch \
    file://0001-drivers-llce-mailbox-change-GFP_KERNEL-to-GFP_ATOMIC.patch \
"
