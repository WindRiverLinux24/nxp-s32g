# Copyright 2023,2024 NXP

DEPENDS:append = " atf-s32g-tools-native"

SECBOOT_PUBKEYS_DIR = "${S}/${HSE_SEC_KEYS}"
SECBOOT_SCRIPT_FILE = "secboot_script.sh"
SECBOOT_SCRIPT = "${SECBOOT_PUBKEYS_DIR}/${SECBOOT_SCRIPT_FILE}"
SECBOOT_DEVICE = "${@bb.utils.contains('UBOOT_MACHINE', 'qspi', '/dev/mtd0', '/dev/mmcblk0', d)}"
OP_TEE = "${@bb.utils.contains('DISTRO_FEATURES', 'optee', '1', '0', d)}"
M7_SEC = "${@bb.utils.contains_any('MACHINE_FEATURES', 'm7_boot secure_boot_parallel', '1', '0', d)}"
M7_BIN_FILE ?= "${@ "m7.bin" if "${M7_SEC}" == '1' else ''}"
M7_SIGN_FILE ?= "${@ "m7-sign.bin" if "${M7_SEC}" == '1' else ''}"

BL2_NAME = "bl2_w_dtb"
BL2_SIGN_NAME = "bl2-signature"

RSA_PUB_KEYS ?= "\
                ${SECBOOT_PUBKEYS_DIR}/${RSA_PUB_BL2} \
                ${SECBOOT_PUBKEYS_DIR}/${RSA_PUB_BL31} \
                ${SECBOOT_PUBKEYS_DIR}/${RSA_PUB_BL33} \
               "

SYSROOT_DIRS:append  = " \
                           ${SECBOOT_KEYS_INSTALL_PATH} \
                       "

do_generate_bl_public_keys () {

    openssl rsa -in ${SECBOOT_SIGN_KEYDIR}/${RSA_PRIV_BL2} -pubout -out ${SECBOOT_PUBKEYS_DIR}/${RSA_PUB_BL2}
    openssl rsa -in ${SECBOOT_SIGN_KEYDIR}/${RSA_PRIV_BL31} -pubout -out ${SECBOOT_PUBKEYS_DIR}/${RSA_PUB_BL31}
    if [ ${OP_TEE} -eq 1 ]; then
        openssl rsa -in ${SECBOOT_SIGN_KEYDIR}/${RSA_PRIV_BL32} -pubout -out ${SECBOOT_PUBKEYS_DIR}/${RSA_PUB_BL32}
    fi
    openssl rsa -in ${SECBOOT_SIGN_KEYDIR}/${RSA_PRIV_BL33} -pubout -out ${SECBOOT_PUBKEYS_DIR}/${RSA_PUB_BL33}
    if [ ${M7_SEC} -eq 1 ]; then
        openssl rsa -in ${SECBOOT_SIGN_KEYDIR}/${RSA_PRIV_M7} -pubout -out ${SECBOOT_PUBKEYS_DIR}/${RSA_PUB_M7}
    fi
}

do_generate_secboot_script() {
	echo -n "hse-secboot -s -d ${SECBOOT_DEVICE}" > ${SECBOOT_SCRIPT}
	echo -n " --bl2_key ${SECBOOT_KEYS_INSTALL_PATH}${RSA_PUB_BL2_FILE}:${BL2_HANDLE}" >> ${SECBOOT_SCRIPT}
	echo -n " --bl31_key ${SECBOOT_KEYS_INSTALL_PATH}${RSA_PUB_BL31_FILE}:${BL31_HANDLE}" >> ${SECBOOT_SCRIPT}
	if [ ${OP_TEE} -eq 1 ]; then
		echo -n " --bl32_key ${SECBOOT_KEYS_INSTALL_PATH}${RSA_PUB_BL32_FILE}:${BL32_HANDLE}" >> ${SECBOOT_SCRIPT}
	fi
	echo -n " --bl33_key ${SECBOOT_KEYS_INSTALL_PATH}${RSA_PUB_BL33_FILE}:${BL33_HANDLE}" >> ${SECBOOT_SCRIPT}
	echo -n " --bl2_bin ${SECBOOT_KEYS_INSTALL_PATH}${BL2_NAME}*" >> ${SECBOOT_SCRIPT}
	echo -n " --bl2_sign ${SECBOOT_KEYS_INSTALL_PATH}${BL2_SIGN_NAME}*" >> ${SECBOOT_SCRIPT}
	if [ ${M7_SEC} -eq 1 ]; then
		echo -n " --m7_key ${SECBOOT_KEYS_INSTALL_PATH}${RSA_PUB_M7_FILE}:${M7_HANDLE}" >> ${SECBOOT_SCRIPT}
		echo -n " --m7_sign ${SECBOOT_KEYS_INSTALL_PATH}${M7_SIGN_FILE}" >> ${SECBOOT_SCRIPT}
		echo " --m7_bin ${SECBOOT_KEYS_INSTALL_PATH}${M7_BIN_FILE}" >> ${SECBOOT_SCRIPT}
	fi
}

do_generate_bl_public_keys[depends] += "openssl-native:do_populate_sysroot"
addtask do_generate_bl_public_keys after do_compile before do_deploy
addtask do_generate_secboot_script after do_generate_bl_public_keys before do_install

do_compile:prepend () {
	mkdir -p ${SECBOOT_PUBKEYS_DIR}
}


do_deploy:append() {

        for type in ${BOOT_TYPE}; do
            for plat in ${PLATFORM}; do
	        ATF_BINARIES="${B}/${type}/${plat}/${BUILD_TYPE}"
                if [ "${type}" = "sd" ]; then
                    cp -vf ${ATF_BINARIES}/${BL2_NAME}.bin ${DEPLOY_DIR_IMAGE}/${BL2_NAME}-${plat}.bin
                    cp -vf ${ATF_BINARIES}/${BL2_SIGN_NAME}.bin ${DEPLOY_DIR_IMAGE}/${BL2_NAME}-${plat}.bin.sign
                else
                    cp -vf ${ATF_BINARIES}/${BL2_NAME}.bin ${DEPLOY_DIR_IMAGE}/${BL2_NAME}-${plat}_${type}.bin
                    cp -vf ${ATF_BINARIES}/${BL2_SIGN_NAME}.bin ${DEPLOY_DIR_IMAGE}/${BL2_NAME}-${plat}_${type}.bin.sign
                fi
            done
        done

	cp -vf ${RSA_PUB_KEYS} ${DEPLOY_DIR_IMAGE}

	if [ ${OP_TEE} -eq 1 ]; then
		cp -vf ${SECBOOT_PUBKEYS_DIR}/${RSA_PUB_BL32} ${DEPLOY_DIR_IMAGE}
	fi
	if [ ${M7_SEC} -eq 1 ]; then
		cp -vf ${SECBOOT_PUBKEYS_DIR}/${RSA_PUB_M7} ${DEPLOY_DIR_IMAGE}
	fi
}

do_install:append() {
	install -d ${D}${SECBOOT_KEYS_INSTALL_PATH}

        for type in ${BOOT_TYPE}; do
            for plat in ${PLATFORM}; do
                ATF_BINARIES="${B}/${type}/${plat}/${BUILD_TYPE}"
                if [ "${type}" = "sd" ]; then
                    cp -vf ${ATF_BINARIES}/${BL2_NAME}.bin ${D}${SECBOOT_KEYS_INSTALL_PATH}/${BL2_NAME}-${plat}.bin
                    cp -vf ${ATF_BINARIES}/${BL2_SIGN_NAME}.bin ${D}${SECBOOT_KEYS_INSTALL_PATH}/${BL2_NAME}-${plat}.bin.sign
                else
                    cp -vf ${ATF_BINARIES}/${BL2_NAME}.bin ${D}${SECBOOT_KEYS_INSTALL_PATH}/${BL2_NAME}-${plat}_${type}.bin
                    cp -vf ${ATF_BINARIES}/${BL2_SIGN_NAME}.bin ${D}${SECBOOT_KEYS_INSTALL_PATH}/${BL2_NAME}-${plat}_${type}.bin.sign
                fi
            done
        done

	install -m 0666 ${RSA_PUB_KEYS} ${D}${SECBOOT_KEYS_INSTALL_PATH}
	if [ ${OP_TEE} -eq 1 ]; then
		install -m 0666 ${SECBOOT_PUBKEYS_DIR}/${RSA_PUB_BL32} ${D}${SECBOOT_KEYS_INSTALL_PATH}
	fi
	if [ ${M7_SEC} -eq 1 ]; then
		install -m 0666 ${SECBOOT_PUBKEYS_DIR}/${RSA_PUB_M7} ${D}${SECBOOT_KEYS_INSTALL_PATH}
        fi

	install -m 0777 ${SECBOOT_SCRIPT} ${D}${SECBOOT_KEYS_INSTALL_PATH}
}

FILES:${PN} += "${SECBOOT_KEYS_INSTALL_PATH}${BL2_SIGN_NAME}-*"
FILES:${PN} += "${SECBOOT_KEYS_INSTALL_PATH}${BL2_NAME}-*"
FILES:${PN} += "${SECBOOT_KEYS_INSTALL_PATH}${RSA_PUB_BL2}"
FILES:${PN} += "${SECBOOT_KEYS_INSTALL_PATH}${RSA_PUB_BL31}"
FILES:${PN} += "${@ "${SECBOOT_KEYS_INSTALL_PATH}${RSA_PUB_BL32}" if "${OP_TEE}" == '1' else ''}"
FILES:${PN} += "${@ "${SECBOOT_KEYS_INSTALL_PATH}${RSA_PUB_M7}" if "${M7_SEC}" == '1' else ''}"
FILES:${PN} += "${SECBOOT_KEYS_INSTALL_PATH}${RSA_PUB_BL33}"
FILES:${PN} += "${SECBOOT_KEYS_INSTALL_PATH}${SECBOOT_SCRIPT_FILE}"
