# Copyright 2021 NXP
#
SUMMARY = "Sample M7 Bootloader"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.BSD;md5=0f00d99239d922ffd13cabef83b33444"

URL ?= "git://github.com/nxp-auto-linux/m7-sample;protocol=https"
RELEASE_BASE ?= "release/bsp33.0"
BRANCH ?= "${RELEASE_BASE}"
SRC_URI = "${URL};branch=${BRANCH}"
SRCREV ?= "39104f1b57cc40cb1e21042cfab01d25fde638ee"

S = "${WORKDIR}/git"
BUILD = "${WORKDIR}/build"

do_compile() {
	mkdir -p "${BUILD}"

	if echo ${UBOOT_CONFIG} | grep -q s32g; then
		plats="$(echo ${UBOOT_CONFIG} | sed 's/_[^ ]*//g' | tr ' ' '\n' | sort -u)"
	else
		plats="${UBOOT_CONFIG}"
	fi

	for suffix in ${BOOT_TYPE}
	do
		for plat in ${plats}; do
			if [ "$suffix" = "sd" ]; then
				ivt_file="atf-${plat}.s32"
			else
				ivt_file="atf-${plat}_${suffix}.s32"
			fi

			BDIR="${BUILD}-${suffix}-${plat}"

			oe_runmake CROSS_COMPILE="arm-none-eabi-" \
				BUILD="${BDIR}" clean

			mkdir -p "${BDIR}"
			cp -vf "${DEPLOY_DIR_IMAGE}/${ivt_file}" "${BDIR}/"

			oe_runmake CROSS_COMPILE="arm-none-eabi-" \
				BUILD="${BDIR}" \
				A53_BOOTLOADER="${BDIR}/${ivt_file}"

			cp  "${BDIR}/${ivt_file}.m7" "${BUILD}"
		done
	done
}

do_deploy() {
	install -d ${DEPLOY_DIR_IMAGE}

	if echo ${UBOOT_CONFIG} | grep -q s32g; then
		plats="$(echo ${UBOOT_CONFIG} | sed 's/_[^ ]*//g' | tr ' ' '\n' | sort -u)"
	else
		plats="${UBOOT_CONFIG}"
	fi

	for suffix in ${BOOT_TYPE}
	do
		for plat in ${plats}; do
			if [ "$suffix" = "sd" ]; then
				ivt_file="atf-${plat}.s32"
			else
				ivt_file="atf-${plat}_${suffix}.s32"
			fi

			cp -vf "${BUILD}/${ivt_file}.m7" "${DEPLOY_DIR_IMAGE}/"
		done
	done
}

addtask deploy after do_compile

do_compile[depends] += "atf-s32g:do_deploy"
DEPENDS += "cortex-m-toolchain-native"
# hexdump native (used by append_m7.sh) dependency
DEPENDS += "util-linux-native"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
