FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:nxp-s32g = " \
    file://0001-configs-s32g2xx-enable-CONFIG_FIT_SIGNATURE-for-secu.patch \
"

do_install:append:nxp-s32g() {
	[ "${UBOOT_SIGN_ENABLE}" = "1" -o -n "${UBOOT_DTB_BINARY}" ] || return

	local -a ma=( ${UBOOT_MACHINE} )
	local -a ca=( ${UBOOT_CONFIG} )

	[ "${#ma[@]}" != "${#ca[@]}" ] && { bbwarn "Non matched number of UBOOT_MACHINE and UBOOT_CONFIG"; return; }

	local -i i=0
	while ((i < ${#ma[@]})); do
		config=${ca[i]}
		cd ${B}/${ma[i]}
		install -Dm 0644 ${UBOOT_DTB_BINARY} ${D}${datadir}/u-boot-${MACHINE}-${PV}-${PR}-${config}.dtb
		ln -sf u-boot-${MACHINE}-${PV}-${PR}-${config}.dtb ${D}${datadir}/u-boot-${config}.dtb
		i=$(expr $i + 1);
	done
}

do_deploy:append:nxp-s32g() {
	[ "${UBOOT_SIGN_ENABLE}" = "1" ] || return

	local -a ma=( ${UBOOT_MACHINE} )
	local -a ca=( ${UBOOT_CONFIG} )

	[ "${#ma[@]}" != "${#ca[@]}" ] && { bbwarn "Non matched number of UBOOT_MACHINE and UBOOT_CONFIG"; return; }

	local -i i=0
	while ((i < ${#ma[@]})); do
		config=${ca[i]}
		cd ${B}/${ma[i]}
		deployed_uboot_dtb_binary=${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}-${PV}-${PR}-${config}.dtb
		if [ "x${UBOOT_SUFFIX}" = "ximg" -o "x${UBOOT_SUFFIX}" = "xrom" ] && \
			[ -e "$deployed_uboot_dtb_binary" ]; then
				cp -f $deployed_uboot_dtb_binary ${B}/${ma[i]}/${UBOOT_DTB_BINARY}
				oe_runmake EXT_DTB=$deployed_uboot_dtb_binary ${UBOOT_MAKE_TARGET}
		fi
		cp -f ${B}/${ma[i]}/${UBOOT_BINARY} ${DEPLOYDIR}/${config}/${UBOOT_BINARY}
		if [ "${config}" = "s32g2xxaevb" ]; then
			sed -i 's|${HSE_FW_DEFAULT_NAME}|${HSE_LOCAL_FIRMWARE_DIR}/${HSE_FW_NAME_S32G2}|g' ${UBOOT_CFGOUT}
		fi
		cp -f ${B}/${ma[i]}/${UBOOT_CFGOUT} ${DEPLOYDIR}/${config}/tools/${UBOOT_CFGOUT}
		i=$(expr $i + 1);
	done
}
