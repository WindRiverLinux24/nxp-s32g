# Add public key to U-Boot dtb for multiple platforms
fitimage_assemble:append:nxp-s32g() {
	[ "${UBOOT_SIGN_ENABLE}" = "1" ] || return

	for config in ${UBOOT_CONFIG}; do
		${UBOOT_MKIMAGE_SIGN} \
			${@'-D "${UBOOT_MKIMAGE_DTCOPTS}"' if len('${UBOOT_MKIMAGE_DTCOPTS}') else ''} \
			-f $1 \
			arch/${ARCH}/boot/$2-$config

		add_key_to_u_boot=""
		if [ -n "${UBOOT_DTB_BINARY}" ]; then
			add_key_to_u_boot="-K ${B}/u-boot-$config.dtb"
		fi
		${UBOOT_MKIMAGE_SIGN} \
			${@'-D "${UBOOT_MKIMAGE_DTCOPTS}"' if len('${UBOOT_MKIMAGE_DTCOPTS}') else ''} \
			-F -k "${UBOOT_SIGN_KEYDIR}" \
			$add_key_to_u_boot \
			-r arch/${ARCH}/boot/$2-$config
	done
}

do_deploy:append:nxp-s32g() {
	[ "${UBOOT_SIGN_ENABLE}" = "1" ] || return

	cd ${B}
	for config in ${UBOOT_CONFIG}; do
		install -m 0644 ${KERNEL_OUTPUT_DIR}/fitImage-$config $deployDir/
	done
}
