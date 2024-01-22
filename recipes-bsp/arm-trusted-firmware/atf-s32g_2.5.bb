# Copyright 2019-2020 NXP

DESCRIPTION = "ARM Trusted Firmware"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

DEPENDS += "dtc-native xxd-native bc-native u-boot-tools-native openssl-native"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

# ATF repository
URL ?= "git://github.com/nxp-auto-linux/arm-trusted-firmware.git;protocol=https"
BRANCH ?= "release/bsp40.0-2.5"
SRC_URI = "${URL};branch=${BRANCH}"
SRCREV ?= "3109dc973f74c76094ac55d8e1356c5947a5caa7"
SRC_URI[sha256sum] = "15d263b62089b46375effede12a1917cd7b267b93dd97c68fd5ddbd1dddede07"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:" 
SRC_URI += " \
    file://0001-fix-auth-forbid-junk-after-extensions.patch \
    file://0002-fix-auth-require-at-least-one-extension-to-be-presen.patch \
    file://0003-fix-auth-avoid-out-of-bounds-read-in-auth_nvctr.patch \
    file://0004-fix-auth-properly-validate-X.509-extensions.patch \
    file://0001-s32_common.mk-Fix-DTC_VERSION.patch \
    file://0001-Makefile-Add-BUILD_PLAT-to-FORCE-s-order-only-prereq.patch \
    file://0001-s32g-evb-usb-remove-usb-phy-device-node.patch \
    file://0001-s32-clk-Return-the-preset-freq-when-we-can-t-calcula.patch \
    file://0001-s32_common.mk-Print-error-message-for-debugging.patch \
    file://rwx-segments.patch \
    file://0001-s32-extend-the-DTB-size-for-BL33.patch \
"

PATCHTOOL = "git"
PLATFORM = "s32g2xxaevb s32g274ardb2 s32g399ardb3 s32g3xxaevb"
BUILD_TYPE = "release"

ATF_S32G_ENABLE = "1"

HSE_BUILD_OPT = "HSE_SUPPORT"
RSA_PRIV_FIP ?= "${B}/${HSE_SEC_KEYS}/${HSE_SEC_PRI_KEY}"

HSE_ARGS = " \
              HSE_SUPPORT=1 \
              "

SECBOOT_ARGS = " \
                 SECBOOT_SUPPORT=1 \
                 RSA_PRIV_FIP=${RSA_PRIV_FIP} \
                 "

EXTRA_OEMAKE += " \
                CROSS_COMPILE=${TARGET_PREFIX} \
                ARCH=${TARGET_ARCH} \
                BUILD_BASE=${B} \
                "

M7BOOT_ARGS = " FIP_OFFSET_DELTA=0x2000"
EXTRA_OEMAKE += "${@bb.utils.contains('MACHINE_FEATURES', 'm7_boot', '${M7BOOT_ARGS}', '', d)}"

# FIXME: Allow linking of 'tools' binaries with native libraries
#        used for generating the boot logo and other tools used
#        during the build process.
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CPPFLAGS} ${BUILD_LDFLAGS}" \
                 HOSTLD="${BUILD_LD}" \
                 OPENSSL_DIR="${STAGING_DIR_NATIVE}" \
                 LIBPATH="${STAGING_LIBDIR_NATIVE}" \
                 HOSTSTRIP=true'

EXTRA_OEMAKE += "${@['', '${HSE_ARGS}']['s32g' in d.getVar('MACHINE') and d.getVar('HSE_SEC_ENABLED') == '1']}"
EXTRA_OEMAKE += "${@['', '${SECBOOT_ARGS}']['s32g' in d.getVar('MACHINE') and d.getVar('ATF_SIGN_ENABLE') == '1']}"

# There are 256 bytes space following IVT, it is able to be used save BSP specific flags
# Boot Types, offset is 0x1100 from the beginning of bootloader image
# The default value 0 represents for Non-secboot, it doesn't need to set it explicitly.
boot_type_off_sd = "4352"
boot_type_off_qspi = "256"
non_secboot = "00000000"
a53_secboot = "00000001"
m7_secboot = "00000002"
nxp_parallel_secboot = "00000003"

str2bin () {
	# write binary as little endian
	print_cmd=`which printf`
	$print_cmd $(echo $1 | sed -E -e 's/(..)(..)(..)(..)/\4\3\2\1/' -e 's/../\\x&/g')
}

generate_hse_keys () {
    hse_keys_dir="${B}/${HSE_SEC_KEYS}"
    if [ -n "${FIP_SIGN_KEYDIR}" ]; then
        hse_pri_key="${FIP_SIGN_KEYDIR}/${HSE_SEC_PRI_KEY}"
    else
        hse_pri_key="${hse_keys_dir}/${HSE_SEC_PRI_KEY}"
    fi

    if [ ! -d "${hse_keys_dir}" ]; then
        install -d ${hse_keys_dir}
        if [ -z "${FIP_SIGN_KEYDIR}" ]; then
            openssl genrsa -out ${hse_keys_dir}/${HSE_SEC_PRI_KEY}
        else
            cp -v ${FIP_SIGN_KEYDIR}/${HSE_SEC_PRI_KEY} ${hse_keys_dir}
        fi
        openssl rsa -in ${hse_pri_key} -outform DER -pubout -out ${hse_keys_dir}/${HSE_SEC_PUB_KEY}
        openssl rsa -in ${hse_pri_key} -outform PEM -pubout -out ${hse_keys_dir}/${HSE_SEC_PUB_KEY_PEM}
        cp -v ${hse_keys_dir}/${HSE_SEC_PUB_KEY} ${DEPLOY_DIR_IMAGE}/
        cp -v ${hse_keys_dir}/${HSE_SEC_PUB_KEY_PEM} ${DEPLOY_DIR_IMAGE}/
    fi
}

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

            if ${@bb.utils.contains('DISTRO_FEATURES', 'optee', 'true', 'false', d)}; then
                optee_plat="$(echo $plat | cut -c1-5)"
                optee_arg="BL32=${DEPLOY_DIR_IMAGE}/optee/$optee_plat/tee-header_v2.bin \
			BL32_EXTRA1=${DEPLOY_DIR_IMAGE}/optee/$optee_plat/tee-pager_v2.bin \
			SPD=opteed"
            fi

            if [ "${ATF_SIGN_ENABLE}" = "1" ]; then
                generate_hse_keys
            fi

            oe_runmake -C ${S} BUILD_BASE=$build_base PLAT=${plat} BL33=$bl33_bin BL33DIR=$bl33_dir MKIMAGE_CFG=$uboot_cfg MKIMAGE=mkimage $optee_arg all

        done
    done
}

do_install() {
    install -d ${D}/boot
    for type in ${BOOT_TYPE}; do
        for plat in ${PLATFORM}; do
            ATF_BINARIES="${B}/${type}/${plat}/${BUILD_TYPE}"
            if [ "${type}" = "sd" ]; then
                cp -v ${ATF_BINARIES}/fip.s32 ${D}/boot/atf-${plat}.s32
            else
                cp -v ${ATF_BINARIES}/fip.s32 ${D}/boot/atf-${plat}_${type}.s32
            fi
        done
    done
}

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}

    for type in ${BOOT_TYPE}; do
        for plat in ${PLATFORM}; do
            ATF_BINARIES="${B}/$type/${plat}/${BUILD_TYPE}"

            if [ "${ATF_SIGN_ENABLE}" = "1" ]; then
                # Set the boot type
                secboot_type=${a53_secboot}
                if ${@bb.utils.contains('MACHINE_FEATURES', 'm7_boot', 'true', 'false', d)}; then
                    secboot_type=${m7_secboot}
                    if ${@bb.utils.contains('MACHINE_FEATURES', 'secure_boot_parallel', 'true', 'false', d)}; then
                        secboot_type=${nxp_parallel_secboot}
                    fi
                fi

                if [ "${type}" = "sd" ]; then
                    boot_type_off=${boot_type_off_sd}
                else
                    boot_type_off=${boot_type_off_qspi}
                fi

                str2bin ${secboot_type} | dd of="${ATF_BINARIES}/fip.s32" count=4 seek=${boot_type_off} \
                                      conv=notrunc,fsync status=none iflag=skip_bytes,count_bytes oflag=seek_bytes

                if [ "${type}" = "sd" ]; then
                    cp -v ${ATF_BINARIES}/fip.s32 ${D}/boot/atf-${plat}.s32
                else
                    cp -v ${ATF_BINARIES}/fip.s32 ${D}/boot/atf-${plat}_${type}.s32
                fi
            fi

            if [ "${type}" = "sd" ]; then
                cp -v ${ATF_BINARIES}/fip.s32 ${DEPLOY_DIR_IMAGE}/atf-${plat}.s32
            else
                cp -v ${ATF_BINARIES}/fip.s32 ${DEPLOY_DIR_IMAGE}/atf-${plat}_${type}.s32
            fi
        done
    done
}

addtask deploy after do_compile before do_package 

do_compile[depends] = "virtual/bootloader:do_deploy"
do_compile[depends] += "${@bb.utils.contains('DISTRO_FEATURES', 'optee', 'optee-os:do_deploy', '', d)}"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
FILES:${PN} += "/boot/*"
