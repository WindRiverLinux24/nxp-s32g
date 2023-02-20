# Copyright 2019-2020 NXP

DESCRIPTION = "ARM Trusted Firmware"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

DEPENDS += "dtc-native xxd-native bc-native u-boot-s32-tools-native openssl-native"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

# ATF repository
URL ?= "git://github.com/nxp-auto-linux/arm-trusted-firmware.git;protocol=https"
BRANCH ?= "release/bsp35.0-2.5"
SRC_URI = "${URL};branch=${BRANCH}"
SRCREV ?= "08ef0179cff364150f98c1257ffaae15272c21bd"
SRC_URI[sha256sum] = "15d263b62089b46375effede12a1917cd7b267b93dd97c68fd5ddbd1dddede07"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:" 
SRC_URI += " \
    file://0001-fix-auth-forbid-junk-after-extensions.patch \
    file://0002-fix-auth-require-at-least-one-extension-to-be-presen.patch \
    file://0003-fix-auth-avoid-out-of-bounds-read-in-auth_nvctr.patch \
    file://0004-fix-auth-properly-validate-X.509-extensions.patch \
    file://0001-Revert-secboot-add-support-for-userspace-secboot.patch \
    file://0001-s32_common.mk-Fix-DTC_VERSION.patch \
    file://0001-Makefile-Add-BUILD_PLAT-to-FORCE-s-order-only-prereq.patch \
    file://0001-s32g-evb-usb-remove-usb-phy-device-node.patch \
    file://0001-s32-clk-Return-the-preset-freq-when-we-can-t-calcula.patch \
    file://0001-s32_common.mk-Print-error-message-for-debugging.patch \
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

M7BOOT_ARGS = " FIP_OFFSET_DELTA=0x2000"
EXTRA_OEMAKE += "${@bb.utils.contains('S32G_FEATURES', 'm7_boot', '${M7BOOT_ARGS}', '', d)}"

# FIXME: Allow linking of 'tools' binaries with native libraries
#        used for generating the boot logo and other tools used
#        during the build process.
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CPPFLAGS} ${BUILD_LDFLAGS}" \
                 HOSTLD="${BUILD_LD}" \
                 OPENSSL_DIR="${STAGING_DIR_NATIVE}" \
                 LIBPATH="${STAGING_LIBDIR_NATIVE}" \
                 HOSTSTRIP=true'

do_compile() {
    unset LDFLAGS
    unset CFLAGS
    unset CPPFLAGS

    for type in ${BOOT_TYPE}; do
        for plat in ${PLATFORM}; do
            build_base="${B}/$type/"
            ATF_BINARIES="${B}/$type/${plat}/${BUILD_TYPE}"
            bl33_dir="${DEPLOY_DIR_IMAGE}/${plat}_${type}"
            if [ "$type" = "sd" ]; then
                bl33_dir="${DEPLOY_DIR_IMAGE}/${plat}"
            fi
            bl33_bin="${bl33_dir}/${UBOOT_BINARY}"
            uboot_cfg="${bl33_dir}/${UBOOT_CFGOUT}"

            if [ "$type" = "qspi" ]; then
                #Now QSPI boot mode not support HSE secboot feature, so disgarding HSE enabled or not, just build it
                oe_runmake -C ${S} BUILD_BASE=$build_base PLAT=${plat} BL33=$bl33_bin BL33DIR=$bl33_dir MKIMAGE_CFG=$uboot_cfg MKIMAGE=mkimage all
            else
                if [ "${HSE_SEC_ENABLED}" = "1" ]; then
                    oe_runmake -C ${S} BUILD_BASE=$build_base PLAT=${plat} BL33=$bl33_bin BL33DIR=$bl33_dir MKIMAGE_CFG=$uboot_cfg MKIMAGE=mkimage HSE_SECBOOT=1 all
                    #get layout of fip.s32
                    mkimage -l ${ATF_BINARIES}/fip.s32 > ${ATF_BINARIES}/atf_layout 2>&1
                    #get "Load address" from fip layout, i.e. the FIP_MEMORY_OFFSET
                    fip_offset=`cat ${ATF_BINARIES}/atf_layout | grep "Load address" | awk -F " " '{print $3}'`
                    oe_runmake -C ${S} BUILD_BASE=$build_base PLAT=${plat} BL33=$bl33_bin BL33DIR=$bl33_dir MKIMAGE_CFG=$uboot_cfg MKIMAGE=mkimage \
                               FIP_MEMORY_OFFSET=$fip_offset HSE_SECBOOT=1 all
                else
                    oe_runmake -C ${S} BUILD_BASE=$build_base PLAT=${plat} BL33=$bl33_bin BL33DIR=$bl33_dir MKIMAGE_CFG=$uboot_cfg MKIMAGE=mkimage all
                fi
            fi

        done
    done
}

do_install() {
    install -d ${D}/boot
    for plat in ${PLATFORM}; do
        ATF_BINARIES="${B}/sd/${plat}/${BUILD_TYPE}"
        cp -v ${ATF_BINARIES}/fip.s32 ${D}/boot/atf-${plat}.s32
    done
}

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}

    for type in ${BOOT_TYPE}; do
        for plat in ${PLATFORM}; do
            ATF_BINARIES="${B}/$type/${plat}/${BUILD_TYPE}"
            hse_keys_dir="${B}/${HSE_SEC_KEYS}"

            if [ "${HSE_SEC_ENABLED}" = "1" ] && [ "${type}" = "sd" ]; then
                if [ -n "${FIP_SIGN_KEYDIR}" ]; then
                    hse_pri_key="${FIP_SIGN_KEYDIR}/${HSE_SEC_PRI_KEY}"
                else
                    hse_pri_key="${hse_keys_dir}/${HSE_SEC_PRI_KEY}"
                fi

                if [ ! -d "${hse_keys_dir}" ]; then
                    install -d ${hse_keys_dir}
                    if [ -z "${FIP_SIGN_KEYDIR}" ]; then
                        openssl genrsa -out ${hse_keys_dir}/${HSE_SEC_PRI_KEY}
                    fi
                    openssl rsa -in ${hse_pri_key} -outform DER -pubout -out ${hse_keys_dir}/${HSE_SEC_PUB_KEY}
                    openssl rsa -in ${hse_pri_key} -outform PEM -pubout -out ${hse_keys_dir}/${HSE_SEC_PUB_KEY_PEM}
                fi

                #calc the offset of need-to-sign part for fip.bin, it is same as the offset of "Trusted Boot Firmware BL2 certificate"
                bl2_cert_line=`${S}/tools/fiptool/fiptool info ${ATF_BINARIES}/fip.bin | grep "Trusted Boot Firmware BL2 certificate"`
                sign_offset=`echo ${bl2_cert_line} | awk -F "," '{print $1}' | awk -F "=" '{print $2}'`

                #take the need-to-sign part of fip.bin
                dd if=${ATF_BINARIES}/fip.bin of=${ATF_BINARIES}/fip.bin.tmp bs=1 count=`printf "%d" ${sign_offset}` conv=notrunc

                #sign the part
                openssl dgst -sha1 -sign ${hse_pri_key} -out ${ATF_BINARIES}/${HSE_SEC_SIGN_DST} ${ATF_BINARIES}/fip.bin.tmp
                #put the signed part back into fip.bin
                ${S}/tools/fiptool/fiptool update --align 16 --tb-fw-cert ${ATF_BINARIES}/${HSE_SEC_SIGN_DST} ${ATF_BINARIES}/fip.bin

                #get offset of fip.bin, which will be used when dd the fip.bin to SD card
                dd_offset=`cat ${ATF_BINARIES}/atf_layout | grep Application | awk -F ":" '{print $3}' | awk -F " " '{print $1}'`
                echo $dd_offset > ${DEPLOY_DIR_IMAGE}/${plat}_dd_offset
                #copy pub key and signed fip.bin to DEPLOY_DIR_IMAGE
                cp -v ${hse_keys_dir}/${HSE_SEC_PUB_KEY} ${DEPLOY_DIR_IMAGE}/
                cp -v ${hse_keys_dir}/${HSE_SEC_PUB_KEY_PEM} ${DEPLOY_DIR_IMAGE}/
                cp -v ${ATF_BINARIES}/fip.bin ${DEPLOY_DIR_IMAGE}/atf-${plat}.s32.signature

            fi

            if [ "${type}" = "sd" ]; then
                cp -v ${ATF_BINARIES}/fip.s32 ${DEPLOY_DIR_IMAGE}/atf-${plat}.s32
            else
                cp -v ${ATF_BINARIES}/fip.s32 ${DEPLOY_DIR_IMAGE}/atf-${plat}_${type}.s32
            fi
        done
    done
}

addtask deploy after do_compile before do_build

do_compile[depends] = "virtual/bootloader:do_deploy"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
FILES:${PN} += "/boot/*"
