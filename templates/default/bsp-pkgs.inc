IMAGE_INSTALL += "can-utils"
IMAGE_INSTALL += "u-boot-s32"
IMAGE_INSTALL += "libp11"
IMAGE_INSTALL += "atf-s32g"
IMAGE_INSTALL += "linuxptp linuxptp-configs"

NXP_S32G_WIC_INC ?= ""
NXP_S32G_WIC_INC:nxp-s32g = "${@bb.utils.contains('MACHINE_FEATURES', 'm7_boot', 'make_m7_boot_wic.inc', 'make_nxp_s32g_wic.inc', d)}"
require conf/${NXP_S32G_WIC_INC}
