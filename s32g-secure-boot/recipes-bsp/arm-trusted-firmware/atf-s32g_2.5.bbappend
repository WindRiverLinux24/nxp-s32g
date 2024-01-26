FILES:${PN} = "/boot ${datadir}"

SRC_URI:append = " \
    file://0001-secboot-move-bl2-base-address-to-0x34100000-when-sec.patch \
    file://0001-dts-s32-extend-the-hse-reserve-memory-to-8-MB.patch \
"

inherit uboot-config

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

do_compile:append() {
    [ "${ATF_SIGN_ENABLE}" = "1" ] || return

    unset LDFLAGS
    unset CFLAGS
    unset CPPFLAGS

    for type in ${BOOT_TYPE}; do
        unset i j
        for plat in ${PLATFORM}; do
            build_base="${B}/$type/"
            ATF_BINARIES="${B}/${type}/${plat}/${BUILD_TYPE}"
            cp "${STAGING_DIR_HOST}/sysroot-only/fitImage" "${ATF_BINARIES}/fitImage-linux"
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

            i=$(expr $i + 1);
            for dtb in ${ATF_DTB}; do
                j=$(expr $j + 1)
                if  [ $j -eq $i ]; then
                    # Re-sign the kernel in order to add the keys to our dtb
                    ${UBOOT_MKIMAGE_SIGN} \
                       ${@'-D "${UBOOT_MKIMAGE_DTCOPTS}"' if len('${UBOOT_MKIMAGE_DTCOPTS}') else ''} \
                       -F -k "${UBOOT_SIGN_KEYDIR}" \
                       -K "${B}/${type}/${plat}/${BUILD_TYPE}/fdts/${dtb}" \
                       -r ${ATF_BINARIES}/fitImage-linux
                    oe_runmake -C ${S} BUILD_BASE=$build_base PLAT=${plat} BL33=$bl33_bin BL33DIR=$bl33_dir MKIMAGE_CFG=$uboot_cfg MKIMAGE=mkimage $optee_arg all
                fi
            done
            unset j
        done
        unset i
    done
}

do_install:prepend() {
    [ "${ATF_SIGN_ENABLE}" = "1" ] || return

    for type in ${BOOT_TYPE}; do
        for plat in ${PLATFORM}; do
            ATF_BINARIES="${B}/$type/${plat}/${BUILD_TYPE}"

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
        done
    done
}

do_deploy:append() {
    if ${@bb.utils.contains('MACHINE_FEATURES', 'm7_boot', 'true', 'false', d)}; then
        if [ -z "${FIP_SIGN_KEYDIR}" ]; then
            # Copy the private key for signing m7 boot binary
            hse_keys_dir="${B}/${HSE_SEC_KEYS}"
            cp -f ${hse_keys_dir}/${HSE_SEC_PRI_KEY} ${DEPLOY_DIR_IMAGE}/
        fi
    fi
}

KERNEL_PN = "${@d.getVar('PREFERRED_PROVIDER_virtual/kernel')}"
python () {
    if d.getVar('ATF_SIGN_ENABLE') == "1":
        # Make "bitbake atf-s32g" depends fitImage
        d.appendVar('DEPENDS', " " + d.getVar('KERNEL_PN'))
}
