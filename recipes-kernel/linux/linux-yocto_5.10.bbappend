require linux-yocto-nxp-s32g2xx.inc

KBRANCH:nxp-s32g2xx  = "v5.10/standard/nxp-sdk-5.10/nxp-s32g2xx"

LINUX_VERSION:nxp-s32g2xx ?= "5.10.x"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append:nxp-s32g2xx = " \
    file://bsp31/rc4/0001-arm64-dts-s32g-declare-the-GMAC-clock-frequency-for-.patch \
    file://bsp31/rc4/0002-tmu-s32gen1-Fixed-bug-about-Kelvin-to-Celsius-conver.patch \
    file://bsp31/rc4/0003-crypto-hse-update-firmware-interface-to-v1.0.0.patch \
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
