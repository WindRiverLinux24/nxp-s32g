make_m7_sample_wic() {
    set -e
    cd ${IMGDEPLOYDIR}
    if echo ${UBOOT_CONFIG} | grep -q s32g; then
        plats="$(echo ${UBOOT_CONFIG} | sed 's/_[^ ]*//g' | tr ' ' '\n' | sort -u)"
    else
        plats="${UBOOT_CONFIG}"
    fi
    for plat in ${plats}; do
        if [ "${ATF_SIGN_ENABLE}" = "1" ]; then
	        atf_s32_m7="${DEPLOY_DIR_IMAGE}/atf-$plat.s32-secure.m7"
        else
            atf_s32_m7="${DEPLOY_DIR_IMAGE}/atf-$plat.s32.m7"
        fi
        imgname="${IMAGE_BASENAME}${IMAGE_MACHINE_SUFFIX}"
        m7_ofname="${imgname}-${plat}-m7${IMAGE_NAME_SUFFIX}${IMAGE_VERSION_SUFFIX}.wic"
        cp ${IMAGE_NAME}.wic ${m7_ofname}
        dd if=$atf_s32_m7 of=$m7_ofname conv=notrunc seek=512 skip=512 oflag=seek_bytes iflag=skip_bytes
        if [ $plat = "aptiv_cvc_sousa" ] || [ $plat = "aptiv_cvc_fl" ]; then
            mv -f $m7_ofname ${imgname}-m7${IMAGE_NAME_SUFFIX}.wic
            break
        fi
        if [ $plat = "s32g3xxaevb" ]; then
            plat="evb3"
        elif [ $plat = "s32g2xxaevb" ]; then
            plat="evb"
        else
            plat="$(echo $plat | grep -o '....$')"
        fi
        linkname="${imgname}-$plat-m7${IMAGE_NAME_SUFFIX}.wic"
        ln -sf $m7_ofname $linkname
    done
}

do_image_wic[depends] += "m7-sample:do_deploy"
do_image_wic[postfuncs] += "${@bb.utils.contains('MACHINE_FEATURES', 'omit_atf', '', 'make_m7_sample_wic', d)}"
