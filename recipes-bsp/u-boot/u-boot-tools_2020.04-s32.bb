require recipes-bsp/u-boot/u-boot-tools.inc
require u-boot-s32.inc

DESCRIPTION = "U-Boot bootloader tools provided by NXP with focus on S32 chipsets"

PROVIDES:class-native += "u-boot-tools-native"

RCONFLICTS:${PN} = "u-boot-tools"
RCONFLICTS:${PN}-mkimage = "u-boot-tools-mkimage"
RCONFLICTS:${PN}-mkenvimage = "u-boot-tools-mkenvimage"
