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
    file://bsp35/rc4/0001-tools-s32ccimage-Add-option-for-boot-core-selection.patch \
    file://bsp35/rc4/0002-serial-linflex-Get-Linflex-clock-rate-during-initial.patch \
    file://bsp35/rc4/0003-phy-s32cc-serdes-rename-fsl-sys-mode-to-nxp-sys-mode.patch \
    file://bsp35/rc4/0004-arm-s32cc-remove-S32_CMU-drive-leftover.patch \
    file://bsp35/rc4/0005-misc-s32cc-cmu-add-S32CC_CMU-config-option.patch \
    file://bsp35/rc4/0006-arm-s32cc-NXP_S32CC-imply-S32CC_CMU-driver.patch \
    file://bsp35/rc7/0001-board-s32gxxxardb-add-RDB3-rev-F-support.patch \
    file://bsp35/rc7/0002-board-s32gxxxardb-Simplify-setting-pfe1_phy_addr.patch \
    file://bsp35/rc7/0003-pci-Update-to-use-new-sequence-numbers.patch \
    file://bsp35/rc7/0004-pci-Update-the-highest-subordinate-bus-number-for-br.patch \
    file://bsp35/rc7/0005-pci-Fix-setting-controller-s-last_busno.patch \
    file://bsp35/rc7/0006-s32cc-pcie-Group-all-device-tree-related-checks-in-o.patch \
    file://bsp35/rc7/0007-s32cc-pcie-Check-if-the-pcie-node-is-enabled.patch \
    file://bsp35/rc7/0008-s32cc-Disable-SerDes-and-PCIe-if-not-listed-in-hwcon.patch \
    file://bsp35/rc7/0009-s32cc-pcie-Update-access-to-config-space.patch \
    file://bsp35/rc7/0010-s32cc-pcie-Updated-some-messages-to-show-the-device-.patch \
    file://bsp35/rc7/0011-dm-uclass-Start-seq-numbers-from-parent-s-for-aliase.patch \
    file://bsp35/rc7/0012-s32cc-pcie-Lower-severity-of-some-mesages.patch \
    file://bsp35/rc7/0013-s32cc-serdes-hwconfig-Fix-several-memory-issues-in-a.patch \
    file://bsp35/rc7/0014-s32-pcie-Remove-always-used-macros.patch \
    file://bsp35/rc7/0015-s32cc-serdes-Enable-mode5-in-Linux-device-tree.patch \
    file://bsp35/rc7/0016-s32cc-serdes-hwconfig-Extend-dts_node-functionality.patch \
    file://bsp35/rc7/0017-s32cc-serdes-Fixup-device-tree-for-Mode-5.patch \
    file://bsp35/rc7/0018-s32-serdes-hwconfig-Redesign-hwconfig-env-variable.patch \
    file://bsp35/rc7/0019-s32-serdes-hwconfig-Update-Kconfigs-for-the-new-form.patch \
    file://bsp35/rc9/0001-bitops-Add-BIT_32-and-BIT_64-macros.patch \
    file://bsp35/rc9/0002-linux-kernel.h-Add-utilities-for-overflow-checking.patch \
    file://bsp35/rc9/0003-linux-kernel.h-Add-a-mechanism-to-detect-size_t-over.patch \
    file://bsp35/rc9/0004-include-log.h-Return-0-from-log-function-to-fix-retu.patch \
    file://bsp35/rc9/0005-s32-SerDes-Coverity-fixes.patch \
    file://bsp35/rc9/0006-s32cc-qspi-Fix-potential-overflowed-array-index-read.patch \
    file://bsp35/rc9/0007-s32cc-qspi-Check-a-potential-overflow-on-pointer-der.patch \
    file://bsp35/rc9/0008-pinctrl-s32cc-check-overflows-and-NULL-dereferences.patch \
    file://bsp35/rc9/0009-hwconfig-fixup-Do-not-return-from-ofnode_append_prop.patch \
    file://bsp35/rc9/0010-net-pfeng-Enable-RMII-mode.patch \
    file://bsp35/rc9/0011-hse-secboot-remove-unused-u-boot-secboot-code.patch \
    file://bsp35/rc9/0012-hse-rename-secboot-options-to-generic-hse.patch \
    file://bsp35/rc9/0013-drivers-net-s32-cc-fix-up-mac-address-for-GMAC.patch \
    file://0001-scripts-mailmapper-python2-python3.patch \
    file://0001-tools-s32gen1_secboot-replace-u-boot.s32-with-u-boot.patch \
    file://0001-tools-s32ccimage-add-reserved-member-for-struct-prog.patch \
    file://0001-u-boot-s32-Makefile-add-scripts_basic-dependency-to-.patch \
    file://0001-configs-Enable-commands-for-ostree.patch \
    file://0001-include-config_distro_bootcmd.h-Check-go-before-boot.patch \
    file://0001-s32g-remove-SAF1508-phy-driver-and-use-common-ulpi-i.patch \
    ${@bb.utils.contains('HSE_SEC_ENABLED', '1', 'file://0001-configs-s32g2xxaevb-add-NXP_HSE_SUPPORT-config.patch', '', d)} \
    ${@bb.utils.contains('HSE_SEC_ENABLED', '1', 'file://0001-configs-s32g274ardb2-add-NXP_HSE_SUPPORT-config.patch', '', d)} \
    ${@bb.utils.contains('HSE_SEC_ENABLED', '1', 'file://0002-configs-s32g399ardb3-add-NXP_HSE_SUPPORT-config.patch', '', d)} \
    ${@bb.utils.contains('HSE_SEC_ENABLED', '1', 'file://0003-configs-s32g3xxaevb-add-NXP_HSE_SUPPORT-config.patch', '', d)} \
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
