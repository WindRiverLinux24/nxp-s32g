PROVIDES = "atf-s32g"
FILES:${PN} = "/boot ${datadir}"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append:nxp-s32g = " \
    file://0001-s32-extend-the-DTB-size-for-BL33.patch \
    file://0001-secboot-move-bl2-base-address-to-0x34100000-when-sec.patch \
"

do_install:append:nxp-s32g() {
	[ "${ATF_SIGN_ENABLE}" = "1" ] || return

	unset i j
	for plat in ${PLATFORM}; do
		i=$(expr $i + 1);
		for dtb in ${ATF_DTB}; do
			j=$(expr $j + 1)
			if  [ $j -eq $i ]; then
				cd ${B}/${plat}/${BUILD_TYPE}/fdts
				install -Dm 0644 ${dtb} ${D}${datadir}/atf-${dtb}
			fi
		done
		unset j
	done
	unset i
}

do_deploy:prepend:nxp-s32g() {
	[ "${ATF_SIGN_ENABLE}" = "1" ] || return

	install -d ${DEPLOY_DIR_IMAGE}

	unset LDFLAGS
	unset CFLAGS
	unset CPPFLAGS

	unset i j
	for plat in ${PLATFORM}; do
		ATF_BINARIES="${B}/${plat}/${BUILD_TYPE}"
		bl33_bin="${DEPLOY_DIR_IMAGE}/${plat}/${UBOOT_BINARY}"
		uboot_cfg="${DEPLOY_DIR_IMAGE}/${plat}/tools/${UBOOT_CFGOUT}"
		i=$(expr $i + 1);
		for dtb in ${ATF_DTB}; do
			j=$(expr $j + 1)
			if  [ $j -eq $i ]; then
				cp -f ${DEPLOY_DIR_IMAGE}/atf-${dtb} ${B}/${plat}/${BUILD_TYPE}/fdts/${dtb}
				oe_runmake -C ${S} PLAT=${plat} BL33=$bl33_bin MKIMAGE_CFG=$uboot_cfg HSE_SECBOOT=1 all
				#get layout of fip.s32
				${DEPLOY_DIR_IMAGE}/${plat}/tools/mkimage -l ${ATF_BINARIES}/fip.s32 > ${ATF_BINARIES}/atf_layout 2>&1
				#get "Load address" from fip layout, i.e. the FIP_MEMORY_OFFSET
				fip_offset=`cat ${ATF_BINARIES}/atf_layout | grep "Load address" | awk -F " " '{print $3}'`
				oe_runmake -C ${S} PLAT=${plat} BL33=$bl33_bin MKIMAGE_CFG=$uboot_cfg FIP_MEMORY_OFFSET=$fip_offset HSE_SECBOOT=1 all
			fi
		done
		unset j
	done
	unset i
}

KERNEL_PN = "${@d.getVar('PREFERRED_PROVIDER_virtual/kernel')}"
python () {
    if d.getVar('ATF_SIGN_ENABLE') == "1":
        # Make "bitbake atf-s32g -cdeploy" depends the signed dtb files
        d.appendVarFlag('do_deploy', 'depends', ' %s:do_deploy' % d.getVar('KERNEL_PN'))
}
