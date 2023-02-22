SRC_URI:append = " \
	 file://0001-m7_boot-preserve-signature-space-for-m7-boot-code.patch \
"

DEPENDS += "openssl-native"

get_u32 () {
	local file="$1"
	local offset="$2"
	printf "%s" $(od --address-radix=n --format=u4 --skip-bytes="$offset" --read-bytes=4 "$file")
}

get_ivt_offset () {
	local file="$1"
	# 0x600001d1 = 1610613201
	local ivt_token="1610613201"
	# 0x1000 = 4096
	local sd_offset="4096"
	local sd_ivt_token=$(get_u32 "$file" "$sd_offset")

	if [ "$sd_ivt_token" = "$ivt_token" ]
	then
		echo "$sd_offset"
		return
	fi

	bbfatal "Failed to detect IVT offset"
}

str2bin () {
	# write binary as little endian
	printf $(echo $1 | sed -E -e 's/(..)(..)(..)(..)/\4\3\2\1/' -e 's/../\\x&/g')
}

# 0x2000 = 8192
m7_boot_size="8192"
# 0x20 = 32			
app_boot_header_off="32"
# 0x40 = 64
app_code_off="64"
# 0x100 = 256
signature_size="256"
# 0x50 = 80
m7_size_off="80"
m7_load_off="4"
m7_entry_off="8"

# Insert signature partition for m7_boot binary
do_compile:append() {
	cd "${BUILD}"
	for suffix in ${BOOT_TYPE}; do
		for plat in ${plats}; do
			m7_boot_file="m7-${plat}-${suffix}.bin"
			if [ "$suffix" = "sd" ]; then
				ivt_file="atf-${plat}.s32"
			else
				ivt_file="atf-${plat}_${suffix}.s32"
			fi
			m7_ivt_file="${ivt_file}.m7"
			m7_ivt_file_secure="${ivt_file}-secure.m7"
			m7_boot_signature="${ivt_file}-secure.m7.signature"
			ivt_header_off=$(get_ivt_offset "${m7_ivt_file}")
			app_header_off=$(get_u32 "${m7_ivt_file}" $(expr $ivt_header_off + ${app_boot_header_off}))
			m7_boot_off=$(expr $app_header_off + ${app_code_off})

			# Get the final m7 code
			# Because the m7.bin is modified when generating m7_ivt_file, it needs to read the m7.bin out from m7_ivt_file.
			# Moreover it also needs to get the stack as the part of signed data, because the stack also must be in secure memory region
			m7_load_addr=$(get_u32 "${m7_ivt_file}" $(expr $app_header_off + ${m7_load_off}))
			m7_entry_addr=$(get_u32 "${m7_ivt_file}" $(expr $app_header_off + ${m7_entry_off}))
			addr_diff=$(expr $m7_entry_addr - $m7_load_addr)
			m7_file_size=$(expr ${m7_boot_size} - $addr_diff - ${signature_size})
			dd of="${m7_boot_file}" if="${m7_ivt_file}" conv=fsync skip=$(expr $m7_boot_off + $addr_diff) \
			count=${m7_file_size} status=none iflag=skip_bytes,count_bytes oflag=seek_bytes

			# Sign m7_boot code with pre-padding that is from m7_boot_off to the start postion of m7 code
			if [ -n "${FIP_SIGN_KEYDIR}" ]; then
				openssl dgst -sha1 -sign ${FIP_SIGN_KEYDIR}/${HSE_SEC_PRI_KEY} -out ${m7_boot_signature} ${m7_boot_file}
			else
				openssl dgst -sha1 -sign ${DEPLOY_DIR_IMAGE}/${HSE_SEC_PRI_KEY} -out ${m7_boot_signature} ${m7_boot_file}
			fi

			# Write signature
			cp $m7_ivt_file $m7_ivt_file_secure
			dd of="${m7_ivt_file_secure}" if="${m7_boot_signature}" conv=notrunc,fsync seek=$(expr $m7_boot_off + ${m7_boot_size} - ${signature_size}) \
			count=${signature_size} status=none iflag=skip_bytes,count_bytes oflag=seek_bytes

			# Save the size of m7 file with pre-padding
			m7_file_size=$(printf "%08x" $m7_file_size)
			str2bin $m7_file_size | dd of="${m7_ivt_file_secure}" count=4 seek=$(expr $ivt_header_off + ${m7_size_off}) \
			conv=notrunc,fsync status=none iflag=skip_bytes,count_bytes oflag=seek_bytes
		done
	done
}

do_deploy:append() {
	for suffix in ${BOOT_TYPE}; do
		for plat in ${plats}; do
			if [ "$suffix" = "sd" ]; then
				ivt_file="atf-${plat}.s32"
			else
				ivt_file="atf-${plat}_${suffix}.s32"
			fi

			cp -vf "${BUILD}/${ivt_file}-secure.m7" "${DEPLOY_DIR_IMAGE}/"
		done
	done
}
