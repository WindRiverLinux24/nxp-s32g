FILES:${PN} = "/boot ${datadir}"

SRC_URI:append = " \
    file://0001-secboot-move-bl2-base-address-to-0x34100000-when-sec.patch \
    file://0001-dts-s32-extend-the-hse-reserve-memory-to-8-MB.patch \
"

do_install:append() {
    [ "${ATF_SIGN_ENABLE}" = "1" ] || return

    for type in ${BOOT_TYPE}; do
        unset i j
        for plat in ${PLATFORM}; do
            i=$(expr $i + 1);
            for dtb in ${ATF_DTB}; do
                j=$(expr $j + 1)
                if  [ $j -eq $i ]; then
                    cd ${B}/${type}/${plat}/${BUILD_TYPE}/fdts
                    install -Dm 0644 ${dtb} ${D}${datadir}/atf-${type}-${dtb}
                fi
            done
            unset j
        done
        unset i
    done
}

do_deploy:prepend() {
    [ "${ATF_SIGN_ENABLE}" = "1" ] || return

    install -d ${DEPLOY_DIR_IMAGE}

    unset LDFLAGS
    unset CFLAGS
    unset CPPFLAGS

    for type in ${BOOT_TYPE}; do
        unset i j
        for plat in ${PLATFORM}; do
            build_base="${B}/$type/"
            ATF_BINARIES="${B}/${type}/${plat}/${BUILD_TYPE}"
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
                    cp -f ${DEPLOY_DIR_IMAGE}/atf-${type}-${dtb} ${B}/${type}/${plat}/${BUILD_TYPE}/fdts/${dtb}
                    oe_runmake -C ${S} BUILD_BASE=$build_base PLAT=${plat} BL33=$bl33_bin BL33DIR=$bl33_dir MKIMAGE_CFG=$uboot_cfg MKIMAGE=mkimage $optee_arg all
                fi
            done
            unset j
        done
        unset i
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
        # Make "bitbake atf-s32g -cdeploy" depends the signed dtb files
        d.appendVarFlag('do_deploy', 'depends', ' %s:do_deploy' % d.getVar('KERNEL_PN'))
}
