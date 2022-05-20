#
# Copyright (C) 2020 Wind River Systems, Inc.
#

UBOOT_SIGN_ENABLE = "1"
UBOOT_SIGN_KEYDIR = "${LAYER_PATH_s32g-secure-layer}/s32g-sample-key/fitImage-rsa2048-keys"
UBOOT_MKIMAGE_DTCOPTS = "-I dts -O dtb -p 2000"
# Key used to sign configuration part in fit image
UBOOT_SIGN_KEYNAME = "boot_key"
# Key used to sign kernel and dtb parts in fit image
UBOOT_SIGN_IMG_KEYNAME = "boot_key_individual"
FIT_SIGN_INDIVIDUAL  = "1"

UBOOT_ENTRYPOINT = "0x80080000"
UBOOT_LOADADDRESS = "0x80080000"

S32G2XXAEVB_UBOOT_DEFCONFIG_NAME:nxp-s32g = "s32g2xxaevb_defconfig"
S32G274ARDB2_UBOOT_DEFCONFIG_NAME:nxp-s32g = "s32g274ardb2_defconfig"
S32G399ARDB3_UBOOT_DEFCONFIG_NAME:nxp-s32g = "s32g399ardb3_defconfig"
S32G3XXAEVB_UBOOT_DEFCONFIG_NAME:nxp-s32g = "s32g3xxaevb_defconfig"

KERNEL_DEVICETREE:nxp-s32g = "freescale/fsl-s32g2xxa-evb.dtb freescale/fsl-s32g274a-rdb2.dtb \
						freescale/fsl-s32g399a-rdb3.dtb freescale/fsl-s32g3xxa-evb.dtb \
"

IMAGE_BOOT_FILES:nxp-s32g = "${@bb.utils.contains('UBOOT_SIGN_ENABLE', '1', 'fitImage-s32g274ardb2 fitImage-s32g2xxaevb fitImage-s32g399ardb3 fitImage-s32g3xxaevb', \
						'Image fsl-s32g2xxa-evb.dtb fsl-s32g274a-rdb2.dtb fsl-s32g399a-rdb3.dtb fsl-s32g3xxa-evb.dtb', d)}"

UBOOT_SUFFIX = "img"
PREFERRED_PROVIDER_u-boot = "u-boot-s32"
KERNEL_CLASSES  = " kernel-fitimage"

# NXPS32G secure boot feature supports rsa2048.
# Set KERNEL_RAS_TYPE as the expected value. The default one is rsa2048.
KERNEL_RAS_TYPE = "rsa2048"

# NXPS32G secure boot feature supports sha1 and sha256 digest.
# Set KERNEL_CSUM_TYPE as the expected value. The default one is sha1.
KERNEL_CSUM_TYPE = "sha1"
