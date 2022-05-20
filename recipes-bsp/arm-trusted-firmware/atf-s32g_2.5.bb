# Copyright 2019-2020 NXP

DESCRIPTION = "ARM Trusted Firmware"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

DEPENDS += "dtc-native xxd-native bc-native"
DEPENDS += "openssl-native"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

# ATF repository
URL ?= "git://source.codeaurora.org/external/autobsps32/arm-trusted-firmware;protocol=https"
BRANCH ?= "release/bsp32.0-2.5"
SRC_URI = "${URL};branch=${BRANCH}"
SRCREV ?= "b6b0419ec2d8cf84d1bf17cb3cd13d16558d639f"
SRC_URI[sha256sum] = "15d263b62089b46375effede12a1917cd7b267b93dd97c68fd5ddbd1dddede07"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:" 
SRC_URI += " \
    file://bsp33/rc1/0001-plat-nxp-Use-s32ccimage-instead-of-s32gen1image.patch \
    file://bsp33/rc1/0002-ddr-Rename-compatible-of-U-Boot-DT-ddr-node.patch \
    file://bsp33/rc1/0003-pmic-vr5510-rename-compatible-string.patch \
    file://bsp33/rc2/0001-scmi-s32-fix-CLOCK_GET_ATTRIBUTES-response.patch \
    file://bsp33/rc3/0001-plan-nxp-Add-S32G2-BlueBox3-platform.patch \
    file://bsp33/rc3/0002-plat-nxp-Rename-S32G_EMU-to-S32CC_EMU.patch \
    file://bsp33/rc3/0003-plat-nxp-Fix-compilation-for-emulation-environment.patch \
    file://bsp33/rc3/0004-plat-nxp-Add-S32G399A-EMU-platform.patch \
    file://bsp33/rc3/0005-plat-nxp-Add-S32G274A-EMU-platform.patch \
    file://bsp33/rc3/0006-plat-nxp-s32-Update-compatible-strings-from-root-nod.patch \
    file://bsp33/rc3/0007-plat-nxp-Rename-fsl-s32g-ocotp-to-nxp-s32g-ocotp.patch \
    file://bsp33/rc3/0008-plat-nxp-Rename-clocking-compatibles.patch \
    file://bsp33/rc3/0009-plat-nxp-Rename-fsl-s32gen1-qspi-to-nxp-s32cc-qspi.patch \
    file://bsp33/rc3/0010-s32cc-emu-Increase-LinFlex-baudrate-to-max-value.patch \
    file://bsp33/rc3/0011-s32cc-Enable-MMC-driver-on-emulator.patch \
    file://bsp33/rc3/0012-s32cc-Increase-the-DDR-size-to-2GB-on-emulator.patch \
    file://bsp33/rc3/0013-s32cc-Enable-BL31SRAM-and-BL31SSRAM-images-on-emulat.patch \
    file://bsp33/rc3/0014-dt-bindings-Add-definitions-for-reserved-ddr-errata-.patch \
    file://bsp33/rc3/0015-ddr-err050543-Write-polling_needed-flag-into-reserve.patch \
    file://bsp33/rc3/0016-bindings-clock-s32cc-add-CMU-clk.patch \
    file://bsp33/rc3/0017-drivers-s32cc-clk-add-CMU-clk.patch \
    file://bsp33/rc3/0018-s32-Increase-the-number-of-XLAT-Tables.patch \
    file://bsp33/rc5/0001-s32cc-dts-Refactor-reserved-memory-node-usage.patch \
    file://bsp33/rc5/0002-ddr-Check-return-status-of-ddrss_to_normal_mode.patch \
    file://bsp33/rc5/0003-plat-nxp-changed-reset-type-for-LAX-and-RADAR-into-p.patch \
    file://bsp33/rc5/0004-fdts-s32-Use-lowercase-for-addresses.patch \
    file://bsp33/rc5/0005-fdts-s32-Remove-fsl-prefix-from-device-trees-name.patch \
    file://bsp33/rc5/0006-fdts-Rename-s32-gen1.dtsi-to-s32cc.dtsi.patch \
    file://bsp33/rc5/0007-fdts-Rename-s32g274a.dtsi-to-s32g2.dtsi.patch \
    file://bsp33/rc5/0008-fdts-s32cc-Make-FXOSC-part-of-the-soc-node.patch \
    file://bsp33/rc5/0009-fdts-s32cc-Make-DDR-part-of-the-soc-node.patch \
    file://bsp33/rc5/0010-fdts-s32cc-Make-I2C-instances-part-of-the-soc-node.patch \
    file://bsp33/rc5/0011-fdts-s32cc-Rename-I2C-compatible-to-nxp-s32cc-i2c.patch \
    file://bsp33/rc5/0012-fdts-s32cc-Make-WKPU-part-of-the-soc-node.patch \
    file://bsp33/rc5/0013-fdts-s32cc-Rename-WKPU-compatible-to-nxp-s32cc-wkpu.patch \
    file://bsp33/rc5/0014-fdts-Rename-clks-node-to-plat_clks.patch \
    file://bsp33/rc5/0015-fdts-s32cc-Make-ARM-PLL-part-of-the-soc-node.patch \
    file://bsp33/rc5/0016-fdts-s32cc-Make-ARM-DFS-part-of-the-soc-node.patch \
    file://bsp33/rc5/0017-fdts-s32cc-Make-PERIPH-PLL-part-of-the-soc-node.patch \
    file://bsp33/rc5/0018-fdts-s32cc-Make-ARM-DFS-part-of-the-soc-node.patch \
    file://bsp33/rc5/0019-fdts-s32cc-Make-ACCEL-PLL-part-of-the-soc-node.patch \
    file://bsp33/rc5/0020-fdts-s32cc-Make-DDR-PLL-part-of-the-soc-node.patch \
    file://bsp33/rc5/0021-fdts-s32cc-Make-MC_ME-part-of-the-soc-node.patch \
    file://bsp33/rc5/0022-fdts-s32cc-Make-RDC-part-of-the-soc-node.patch \
    file://bsp33/rc5/0023-fdts-s32cc-Make-MC_RGM-part-of-soc-node.patch \
    file://bsp33/rc5/0024-fdts-s32cc-Make-MC_CGM0-part-of-the-soc-node.patch \
    file://bsp33/rc5/0025-fdts-s32cc-Make-MC_CGM1-part-of-th-soc-node.patch \
    file://bsp33/rc5/0026-fdts-s32cc-Add-MC_CGM5-to-the-soc-node.patch \
    file://bsp33/rc5/0027-fdts-s32cc-Make-OCOTP-part-of-the-soc-node.patch \
    file://bsp33/rc5/0028-fdts-s32cc-Make-QSPI-part-of-the-soc-node.patch \
    file://bsp33/rc5/0029-fdts-s32cc-Place-all-clock-generators-in-clocks-node.patch \
    file://bsp33/rc5/0030-fdts-s32g3-Make-MC_CGM6-part-of-the-soc-node.patch \
    file://bsp33/rc5/0031-fdts-s32r45-Make-external-clocks-part-of-the-clocks-.patch \
    file://bsp33/rc5/0032-fdts-s32cc-Make-MC_CGM2-part-of-the-soc-node.patch \
    file://bsp33/rc5/0033-refactor-dt-bindings-align-irq-bindings-with-kernel.patch \
    file://bsp33/rc5/0034-dt-bindings-Add-headers-needed-for-s32cc-device-tree.patch \
    file://bsp33/rc5/0035-dts-gpio-Add-dt-constants-for-s32cc-device-trees.patch \
    file://bsp33/rc5/0036-fdts-s32cc-Sync-U-Boot-and-ATF-device-trees.patch \
    file://bsp33/rc5/0037-fdts-s32cc-Add-upstream-compatible-for-Linflex.patch \
    file://bsp33/rc5/0038-fdts-s32cc-Add-SUSE-compatible-for-SDHC.patch \
    file://bsp33/rc5/0039-fdts-s32cc-Correct-QSPI-simple_bus_reg-warning.patch \
    file://bsp33/rc5/0040-fdts-s32cc-Correct-SerDes-simple_bus_reg-warning.patch \
    file://bsp33/rc5/0041-fdts-s32cc-Correct-RDC-simple_bus_reg-warning.patch \
    file://bsp33/rc5/0042-fdts-s32cc-Correct-RDC-simple_bus_reg-warning.patch \
    file://bsp33/rc5/0043-fdts-s32cc-Correct-PMIC-simple_bus_reg-warnings.patch \
    file://bsp33/rc5/0044-fdts-s32cc-Add-nvmem-cell-properties-to-secondary-co.patch \
    file://bsp33/rc5/0045-s32-Double-license-S32-file-with-GPL-2.0-BSD-3.patch \
    file://bsp33/rc5/0046-s32-Make-use-of-platform-DTB-for-BL33.patch \
    file://bsp33/rc5/0047-s32g-evb-usb-Add-clock.patch \
    file://bsp33/rc5/0048-dts-phy-Add-dt-constants-for-s32cc-device-trees.patch \
    file://bsp33/rc5/0049-fdts-s32cc-Sync-GMAC-bindings.patch \
    file://bsp33/rc5/0050-secboot-add-placeholder-for-signature-in-fip.patch \
    file://0001-Fix-fiptool-build-error.patch \
    file://0001-s32_common.mk-Fix-DTC_VERSION.patch \
    file://0001-Makefile-Add-BUILD_PLAT-to-FORCE-s-order-only-prereq.patch \
"

PATCHTOOL = "git"
PLATFORM = "s32g2xxaevb s32g274ardb2 s32g399ardb3 s32g3xxaevb"
BUILD_TYPE = "release"

ATF_S32G_ENABLE = "1"

EXTRA_OEMAKE += " \
                CROSS_COMPILE=${TARGET_PREFIX} \
                ARCH=${TARGET_ARCH} \
                BUILD_BASE=${B} \
                "

# FIXME: Allow linking of 'tools' binaries with native libraries
#        used for generating the boot logo and other tools used
#        during the build process.
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CPPFLAGS} ${BUILD_LDFLAGS}" \
                 HOSTLD="${BUILD_LD}" \
                 LIBPATH="${STAGING_LIBDIR_NATIVE}" \
                 HOSTSTRIP=true'

do_compile() {
	unset LDFLAGS
	unset CFLAGS
	unset CPPFLAGS

	for plat in ${PLATFORM}; do
		ATF_BINARIES="${B}/${plat}/${BUILD_TYPE}"

		if [ "${plat}" = "s32g2xxaevb" ] && [ "${HSE_SEC_ENABLED}" = "1" ]; then
			oe_runmake -C ${S} PLAT=${plat} BL33="${DEPLOY_DIR_IMAGE}/${plat}/${UBOOT_BINARY}" MKIMAGE_CFG="${DEPLOY_DIR_IMAGE}/${plat}/tools/${UBOOT_CFGOUT}" FIP_MEMORY_OFFSET=0x3407e910 HSE_SECBOOT=1 all
		else
			oe_runmake -C ${S} PLAT=${plat} BL33="${DEPLOY_DIR_IMAGE}/${plat}/${UBOOT_BINARY}" MKIMAGE_CFG="${DEPLOY_DIR_IMAGE}/${plat}/tools/${UBOOT_CFGOUT}" all
		fi
	done
}

do_install() {
	install -d ${D}/boot
	for plat in ${PLATFORM}; do
		ATF_BINARIES="${B}/${plat}/${BUILD_TYPE}"
		cp -v ${ATF_BINARIES}/fip.s32 ${D}/boot/atf-${plat}.s32
	done
}

do_deploy() {
	install -d ${DEPLOY_DIR_IMAGE}
	for plat in ${PLATFORM}; do
		ATF_BINARIES="${B}/${plat}/${BUILD_TYPE}"

		if [ "${plat}" = "s32g2xxaevb" ] && [ "${HSE_SEC_ENABLED}" = "1" ]; then
			install -d ${ATF_BINARIES}/${HSE_SEC_KEYS}
			openssl genrsa -out ${ATF_BINARIES}/${HSE_SEC_KEYS}/${HSE_SEC_PRI_KEY}
			openssl rsa -in ${ATF_BINARIES}/${HSE_SEC_KEYS}/${HSE_SEC_PRI_KEY} -outform DER -pubout -out ${ATF_BINARIES}/${HSE_SEC_KEYS}/${HSE_SEC_PUB_KEY}
			openssl dgst -sha1 -sign ${ATF_BINARIES}/${HSE_SEC_KEYS}/${HSE_SEC_PRI_KEY} -out ${ATF_BINARIES}/${HSE_SEC_SIGN_DST} ${ATF_BINARIES}/${HSE_SEC_SIGN_SRC}
			cp -v ${ATF_BINARIES}/${HSE_SEC_KEYS}/${HSE_SEC_PUB_KEY} ${DEPLOY_DIR_IMAGE}/
			cp -v ${ATF_BINARIES}/${HSE_SEC_SIGN_DST} ${DEPLOY_DIR_IMAGE}/atf-${plat}.s32.signature
		fi

		cp -v ${ATF_BINARIES}/fip.s32 ${DEPLOY_DIR_IMAGE}/atf-${plat}.s32
	done
}

addtask deploy after do_compile

do_compile[depends] = "virtual/bootloader:do_deploy"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
FILES:${PN} += "/boot/*"
