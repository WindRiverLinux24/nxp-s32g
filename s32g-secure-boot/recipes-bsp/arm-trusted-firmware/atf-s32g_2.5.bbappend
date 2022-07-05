PROVIDES = "atf-s32g"
FILES:${PN} = "/boot ${datadir}"

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

do_deploy:append:nxp-s32g() {
	[ "${ATF_SIGN_ENABLE}" = "1" ] || return

	unset LDFLAGS
	unset CFLAGS
	unset CPPFLAGS

	unset i j
	for plat in ${PLATFORM}; do
		i=$(expr $i + 1);
		for dtb in ${ATF_DTB}; do
			j=$(expr $j + 1)
			if  [ $j -eq $i ]; then
				cp -f ${DEPLOY_DIR_IMAGE}/atf-${dtb} ${B}/${plat}/${BUILD_TYPE}/fdts/${dtb}
				if [ "${plat}" = "s32g2xxaevb" ] && [ "${HSE_SEC_ENABLED}" = "1" ]; then
					oe_runmake -C ${S} PLAT=${plat} BL33="${DEPLOY_DIR_IMAGE}/${plat}/${UBOOT_BINARY}" \
						MKIMAGE_CFG="${DEPLOY_DIR_IMAGE}/${plat}/tools/${UBOOT_CFGOUT}" FIP_MEMORY_OFFSET=0x3407e910 HSE_SECBOOT=1 all
				else
					oe_runmake -C ${S} PLAT=${plat} BL33="${DEPLOY_DIR_IMAGE}/${plat}/${UBOOT_BINARY}" \
					MKIMAGE_CFG="${DEPLOY_DIR_IMAGE}/${plat}/tools/${UBOOT_CFGOUT}" all
				fi
			fi
		done
		unset j
	done
	unset i

	for plat in ${PLATFORM}; do
		ATF_BINARIES="${B}/${plat}/${BUILD_TYPE}"
		cp -v ${ATF_BINARIES}/fip.s32 ${D}/boot/atf-${plat}.s32

		if [ "${plat}" = "s32g2xxaevb" ] && [ "${HSE_SEC_ENABLED}" = "1" ]; then
			openssl dgst -sha1 -sign ${ATF_BINARIES}/${HSE_SEC_KEYS}/${HSE_SEC_PRI_KEY} -out ${ATF_BINARIES}/${HSE_SEC_SIGN_DST} ${ATF_BINARIES}/${HSE_SEC_SIGN_SRC}
			cp -v ${ATF_BINARIES}/${HSE_SEC_SIGN_DST} ${DEPLOY_DIR_IMAGE}/atf-${plat}.s32.signature
		fi
		cp -v ${ATF_BINARIES}/fip.s32 ${DEPLOY_DIR_IMAGE}/atf-${plat}.s32
	done
}

KERNEL_PN = "${@d.getVar('PREFERRED_PROVIDER_virtual/kernel')}"
python () {
    if d.getVar('ATF_SIGN_ENABLE') == "1":
        # Make "bitbake atf-s32g -cdeploy" depends the signed dtb files
        d.appendVarFlag('do_deploy', 'depends', ' %s:do_deploy' % d.getVar('KERNEL_PN'))
}
