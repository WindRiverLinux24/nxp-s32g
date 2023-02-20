PROVIDES = "atf-s32g"
FILES:${PN} = "/boot ${datadir}"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " \
    file://0001-s32-extend-the-DTB-size-for-BL33.patch \
    file://0001-secboot-move-bl2-base-address-to-0x34100000-when-sec.patch \
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
                    install -Dm 0644 ${dtb} ${D}${datadir}/atf-${dtb}
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
            i=$(expr $i + 1);
            for dtb in ${ATF_DTB}; do
                j=$(expr $j + 1)
                if  [ $j -eq $i ]; then
                    cp -f ${DEPLOY_DIR_IMAGE}/atf-${dtb} ${B}/${type}/${plat}/${BUILD_TYPE}/fdts/${dtb}
                    oe_runmake -C ${S} BUILD_BASE=$build_base PLAT=${plat} BL33=$bl33_bin BL33DIR=$bl33_dir MKIMAGE_CFG=$uboot_cfg MKIMAGE=mkimage HSE_SECBOOT=1 all
                    #get layout of fip.s32
                    mkimage -l ${ATF_BINARIES}/fip.s32 > ${ATF_BINARIES}/atf_layout 2>&1
                    #get "Load address" from fip layout, i.e. the FIP_MEMORY_OFFSET
                    fip_offset=`cat ${ATF_BINARIES}/atf_layout | grep "Load address" | awk -F " " '{print $3}'`
                    oe_runmake -C ${S} BUILD_BASE=$build_base PLAT=${plat} BL33=$bl33_bin BL33DIR=$bl33_dir MKIMAGE_CFG=$uboot_cfg MKIMAGE=mkimage \
                                FIP_MEMORY_OFFSET=$fip_offset HSE_SECBOOT=1 all
                fi
            done
            unset j
        done
        unset i
    done
}

do_deploy:append() {
    if ${@bb.utils.contains('S32G_FEATURES', 'm7_boot', 'true', 'false', d)}; then
        if [ -z "${FIP_SIGN_KEYDIR}" ]; then
            # Copy the private key for signing m7 boot binary
            hse_keys_dir="${B}/${HSE_SEC_KEYS}"
            cp -f ${hse_keys_dir}/${HSE_SEC_PRI_KEY} ${DEPLOY_DIR_IMAGE}/
        fi

        # Write signed fip.bin into fip.s32
        for type in ${BOOT_TYPE}; do
            for plat in ${PLATFORM}; do
                ATF_BINARIES="${B}/${type}/${plat}/${BUILD_TYPE}"
                fip_dd_offset=`cat ${ATF_BINARIES}/atf_layout | grep Application | awk -F ":" '{print $3}' | awk -F " " '{print $1}'`
                dd if=${ATF_BINARIES}/fip.bin of=${ATF_BINARIES}/fip.s32 seek=`printf "%d" ${fip_dd_offset}` oflag=seek_bytes conv=notrunc,fsync
                cp -f ${ATF_BINARIES}/fip.s32 ${DEPLOY_DIR_IMAGE}/atf-${plat}.s32
            done
        done
    fi
}

KERNEL_PN = "${@d.getVar('PREFERRED_PROVIDER_virtual/kernel')}"
python () {
    if d.getVar('ATF_SIGN_ENABLE') == "1":
        # Make "bitbake atf-s32g -cdeploy" depends the signed dtb files
        d.appendVarFlag('do_deploy', 'depends', ' %s:do_deploy' % d.getVar('KERNEL_PN'))
}
