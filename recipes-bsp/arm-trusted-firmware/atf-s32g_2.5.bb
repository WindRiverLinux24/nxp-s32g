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
BRANCH ?= "release/bsp33.0-2.5"
SRC_URI = "${URL};branch=${BRANCH}"
SRCREV ?= "a420c6d442c9d122a1b72d89c7e964fc64eebd38"
SRC_URI[sha256sum] = "15d263b62089b46375effede12a1917cd7b267b93dd97c68fd5ddbd1dddede07"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:" 
SRC_URI += " \
    file://bsp34/rc1/0001-fdts-s32-change-the-clock-name-from-ts-to-ptp_ref-fo.patch \
    file://bsp34/rc1/0002-s32cc-dts-qspi-Add-QSPI-partitions.patch \
    file://bsp34/rc1/0003-fdts-s32r45-Add-missing-SDHC-clock.patch \
    file://bsp34/rc1/0004-fdts-s32cc-Remove-the-usage-of-device_id-property.patch \
    file://bsp34/rc1/0005-s32cc-Remove-address-from-ATF-reserved-region.patch \
    file://bsp34/rc1/0006-s32cc-cpu-Define-cluster-topology.patch \
    file://bsp34/rc8/0001-dts-qspi-Remove-unnecessary-address-cells-size-cells.patch \
    file://bsp34/rc8/0002-clk-s32-Panic-if-failed-to-initialize-clocks.patch \
    file://bsp34/rc8/0003-clk-s32-Add-more-space-for-fixed-clocks.patch \
    file://bsp34/rc8/0004-fdts-s32cc-Add-SerDes-external-clocks.patch \
    file://bsp34/rc8/0005-fdts-s32g3-move-cpu-clusters-in-cpu-map-node.patch \
    file://bsp34/rc8/0006-fdts-s32cc-probe-a53_gpr-before-relocation.patch \
    file://bsp34/rc8/0007-s32g3-dts-Define-new-QSPI-compatible-string-binding.patch \
    file://bsp34/rc8/0008-fdts-s32cc-remove-unnecessary-gmac-bindings-changes.patch \
    file://bsp34/rc8/0009-s32-mmc-Call-s32_mmc_init-before-sending-MMC-command.patch \
    file://bsp34/rc8/0010-s32-mmc-Use-only-the-TC-bit-to-check-for-transaction.patch \
    file://bsp34/rc8/0011-s32-mmc-Touch-only-the-relevant-INT_STATUS-_EN-bits.patch \
    file://bsp34/rc8/0012-s32-mmc-Set-INT_SIGNAL_EN-in-s32_mmc_init-not-on-eac.patch \
    file://bsp34/rc8/0013-s32-mmc-Set-DTOCV-to-1101b-the-recommended-value.patch \
    file://bsp34/rc8/0014-s32-mmc-Poll-SDSTB-when-changing-the-clock.patch \
    file://bsp34/rc8/0015-s32-mmc-Refactor-logic-for-differentiating-data-tran.patch \
    file://bsp34/rc8/0016-s32-mmc-Use-a-device-data-structure.patch \
    file://bsp34/rc8/0017-dt-bindings-memory-add-s32-siul2.h.patch \
    file://bsp34/rc8/0018-fdts-s32-unify-SIUL2-modules.patch \
    file://bsp34/rc8/0019-clk-s32-Add-more-space-for-fixed-clocks.patch \
    file://bsp34/rc8/0020-fdts-s32cc-Add-reset-line-for-SerDes-nodes.patch \
    file://bsp34/rc8/0021-mk-use-gawk-instead-of-mawk.patch \
    file://bsp34/rc8/0022-fdts-s32cc-serdes-Use-the-same-register-bindings-as-.patch \
    file://bsp34/rc8/0023-fdts-s32cc-serdes-Use-the-same-clock-bindings-as-in-.patch \
    file://bsp34/rc8/0024-fdts-s32-rename-pinctrl_-nodes-and-add-grp-subnode.patch \
    file://bsp34/rc8/0025-dts-s32-switch-to-generic-pinconf-pinmux-interface.patch \
    file://bsp34/rc8/0026-dt-bindings-pinctrl-s32-remove-old-bindings.patch \
    file://bsp34/rc8/0027-fdts-s32-remove-old-dt-bindings-includes.patch \
    file://bsp34/rc8/0028-fdts-s32cc-Use-nvmem-cells-instead-of-links-to-nvmem.patch \
    file://bsp34/rc8/0029-ddr-Update-to-S32CT-DDR-Tool-1.6.patch \
    file://bsp34/rc8/0030-fdts-Add-SerDes-phys-to-PFE-node.patch \
    file://bsp34/rc8/0031-s32gen1-pcie-dts-Sync-max-link-speed-attribute-name-.patch \
    file://bsp34/rc8/0032-clk-s32-initialize-struct-clk.data-member.patch \
    file://bsp34/rc8/0033-s32cc-vr5510-Don-t-keep-PWRON1-as-wake-up-pin-during.patch \
    file://bsp34/rc8/0034-clk-Keep-the-reserved-fields-of-PLLDV-registers.patch \
    file://bsp34/rc8/0035-ddr-Drop-_mmio-suffix-for-ddr_lp.c-and-ddr_utils.c.patch \
    file://bsp34/rc8/0036-ddr-Move-s32cc-platform-includes-to-ddr_plat.h.patch \
    file://bsp34/rc8/0037-ddr-Move-code-to-separate-makefile.patch \
    file://bsp34/rc8/0038-ddr-Add-CUSTOM_DDR_DRV-make-parameter-for-external-d.patch \
    file://bsp34/rc8/0039-s32-Increase-DTB-max-size-to-0x8000.patch \
    file://bsp34/rc8/0040-plat-s32-Replace-ECHO-macro-with-echo-command.patch \
    file://bsp34/rc8/0041-plat-s32-Add-build-folder-as-dependency-to-generated.patch \
    file://bsp34/rc8/0042-fix-fiptool-respect-OPENSSL_DIR.patch \
    file://0001-s32_common.mk-Fix-DTC_VERSION.patch \
    file://0001-Makefile-Add-BUILD_PLAT-to-FORCE-s-order-only-prereq.patch \
    file://0001-s32g-evb-usb-remove-usb-phy-device-node.patch \
    file://0001-s32-clk-Return-the-preset-freq-when-we-can-t-calcula.patch \
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
		bl33_bin="${DEPLOY_DIR_IMAGE}/${plat}/${UBOOT_BINARY}"
		uboot_cfg="${DEPLOY_DIR_IMAGE}/${plat}/tools/${UBOOT_CFGOUT}"

		if [ "${HSE_SEC_ENABLED}" = "1" ]; then
			oe_runmake -C ${S} PLAT=${plat} BL33=$bl33_bin MKIMAGE_CFG=$uboot_cfg HSE_SECBOOT=1 all
			#get layout of fip.s32
			${DEPLOY_DIR_IMAGE}/${plat}/tools/mkimage -l ${ATF_BINARIES}/fip.s32 > ${ATF_BINARIES}/atf_layout 2>&1
			#get "Load address" from fip layout, i.e. the FIP_MEMORY_OFFSET
			fip_offset=`cat ${ATF_BINARIES}/atf_layout | grep "Load address" | awk -F " " '{print $3}'`
			oe_runmake -C ${S} PLAT=${plat} BL33=$bl33_bin MKIMAGE_CFG=$uboot_cfg FIP_MEMORY_OFFSET=$fip_offset HSE_SECBOOT=1 all
		else
			oe_runmake -C ${S} PLAT=${plat} BL33=$bl33_bin MKIMAGE_CFG=$uboot_cfg all
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
		hse_keys_dir="${B}/${HSE_SEC_KEYS}"

		if [ "${HSE_SEC_ENABLED}" = "1" ]; then
			if [ ! -d "${hse_keys_dir}" ]; then
				install -d ${hse_keys_dir}
				openssl genrsa -out ${hse_keys_dir}/${HSE_SEC_PRI_KEY}
				openssl rsa -in ${hse_keys_dir}/${HSE_SEC_PRI_KEY} -outform DER -pubout -out ${hse_keys_dir}/${HSE_SEC_PUB_KEY}
			fi

			#calc the offset of need-to-sign part for fip.bin, it is same as the offset of "Trusted Boot Firmware BL2 certificate"
			bl2_cert_line=`${S}/tools/fiptool/fiptool info ${ATF_BINARIES}/fip.bin | grep "Trusted Boot Firmware BL2 certificate"`
			sign_offset=`echo ${bl2_cert_line} | awk -F "," '{print $1}' | awk -F "=" '{print $2}'`

			#take the need-to-sign part of fip.bin
			dd if=${ATF_BINARIES}/fip.bin of=${ATF_BINARIES}/fip.bin.tmp bs=1 count=`printf "%d" ${sign_offset}` conv=notrunc

			#sign the part
			openssl dgst -sha1 -sign ${hse_keys_dir}/${HSE_SEC_PRI_KEY} -out ${ATF_BINARIES}/${HSE_SEC_SIGN_DST} ${ATF_BINARIES}/fip.bin.tmp
			#put the signed part back into fip.bin
			${S}/tools/fiptool/fiptool update --align 16 --tb-fw-cert ${ATF_BINARIES}/${HSE_SEC_SIGN_DST} ${ATF_BINARIES}/fip.bin

			#get offset of fip.bin, which will be used when dd the fip.bin to SD card
			dd_offset=`cat ${ATF_BINARIES}/atf_layout | grep Application | awk -F ":" '{print $3}' | awk -F " " '{print $1}'`
			echo $dd_offset > ${DEPLOY_DIR_IMAGE}/${plat}_dd_offset
			#copy pub key and signed fip.bin to DEPLOY_DIR_IMAGE
			cp -v ${hse_keys_dir}/${HSE_SEC_PUB_KEY} ${DEPLOY_DIR_IMAGE}/
			cp -v ${ATF_BINARIES}/fip.bin ${DEPLOY_DIR_IMAGE}/atf-${plat}.s32.signature

		fi

		cp -v ${ATF_BINARIES}/fip.s32 ${DEPLOY_DIR_IMAGE}/atf-${plat}.s32
	done
}

addtask deploy after do_compile

do_compile[depends] = "virtual/bootloader:do_deploy"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
FILES:${PN} += "/boot/*"
