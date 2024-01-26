# Add public key to ATF dtb for multiple platforms
fitimage_assemble:append() {
	[ "${ATF_SIGN_ENABLE}" = "1" ] || return
	if [ "${VAULT_ENABLE}" != "1" ]; then
		${UBOOT_MKIMAGE_SIGN} \
			${@'-D "${UBOOT_MKIMAGE_DTCOPTS}"' if len('${UBOOT_MKIMAGE_DTCOPTS}') else ''} \
			-f $1 \
			arch/${ARCH}/boot/$2

		${UBOOT_MKIMAGE_SIGN} \
			${@'-D "${UBOOT_MKIMAGE_DTCOPTS}"' if len('${UBOOT_MKIMAGE_DTCOPTS}') else ''} \
			-F -k "${UBOOT_SIGN_KEYDIR}" \
			-r arch/${ARCH}/boot/$2
	fi
}

SYSROOT_DIRS:append = " /sysroot-only"
do_install:append() {
	if echo ${KERNEL_IMAGETYPES} | grep -wq "fitImage" && \
		[ "${ATF_SIGN_ENABLE}" = "1" ]; then
		install -D ${B}/${KERNEL_OUTPUT_DIR}/fitImage ${D}/sysroot-only/fitImage
	fi
}

do_deploy:append() {
	[ "${ATF_SIGN_ENABLE}" = "1" ] || return

	cd ${B}
	install -m 0644 ${KERNEL_OUTPUT_DIR}/fitImage $deployDir/

	if [ "${OSTREE_USE_FIT}" = "1" ]; then
		cp -af ${UBOOT_SIGN_KEYDIR}/${UBOOT_SIGN_KEYNAME}.key $deployDir/
		cp -af ${UBOOT_SIGN_KEYDIR}/${UBOOT_SIGN_IMG_KEYNAME}.key $deployDir/
	fi
}

# Emit signature of the fitImage ITS kernel section
fitimage_emit_section_kernel:append() {
	if [ "${ATF_SIGN_ENABLE}" = "1" -a "${FIT_SIGN_INDIVIDUAL}" = "1" -a -n "$kernel_sign_keyname" ] ; then
		sed -i '$ d' $1
		cat << EOF >> $1
                        signature-1 {
                                algo = "$kernel_csum,$kernel_sign_algo";
                                key-name-hint = "$kernel_sign_keyname";
                        };
                };
EOF
	fi
}

# Emit signature of the fitImage ITS DTB section
fitimage_emit_section_dtb:append() {
	if [ "${ATF_SIGN_ENABLE}" = "1" -a "${FIT_SIGN_INDIVIDUAL}" = "1" -a -n "$dtb_sign_keyname" ] ; then
		sed -i '$ d' $1
		cat << EOF >> $1
                        signature-1 {
                                algo = "$dtb_csum,$dtb_sign_algo";
                                key-name-hint = "$dtb_sign_keyname";
                        };
                };
EOF
	fi
}

# Emit signature of the fitImage ITS u-boot script section
fitimage_emit_section_boot_script:append() {
	if [ "${ATF_SIGN_ENABLE}" = "1" -a "${FIT_SIGN_INDIVIDUAL}" = "1" -a -n "$bootscr_sign_keyname" ] ; then
		sed -i '$ d' $1
		cat << EOF >> $1
                        signature-1 {
                                algo = "$bootscr_csum,$bootscr_sign_algo";
                                key-name-hint = "$bootscr_sign_keyname";
                        };
                };
EOF
	fi
}

# Emit signature of the fitImage ITS ramdisk section
fitimage_emit_section_ramdisk:append() {
	if [ "${ATF_SIGN_ENABLE}" = "1" -a "${FIT_SIGN_INDIVIDUAL}" = "1" -a -n "$ramdisk_sign_keyname" ] ; then
		sed -i '$ d' $1
		cat << EOF >> $1
                        signature-1 {
                                algo = "$ramdisk_csum,$ramdisk_sign_algo";
                                key-name-hint = "$ramdisk_sign_keyname";
                        };
                };
EOF
	fi
}

# set key name of the fitImage ITS configuration section
fitimage_emit_section_config:prepend() {
	if [ "${ATF_SIGN_ENABLE}" = "1" ] ; then
		conf_sign_keyname="${UBOOT_SIGN_KEYNAME}"
	fi
}
