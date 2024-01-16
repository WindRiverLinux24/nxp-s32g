require recipes-bsp/u-boot/u-boot-tools.inc
require u-boot-s32.inc

DESCRIPTION = "U-Boot bootloader tools provided by NXP with focus on S32 chipsets"

DEPENDS += "gnutls util-linux"

PROVIDES:class-native += "u-boot-tools-native"

RCONFLICTS:${PN} = "u-boot-tools"
RCONFLICTS:${PN}-mkimage = "u-boot-tools-mkimage"
RCONFLICTS:${PN}-mkenvimage = "u-boot-tools-mkenvimage"

do_compile () {
    oe_runmake -C ${S} tools-only_defconfig O=${B}
    oe_runmake -C ${S} cross_tools NO_SDL=1 O=${B}
}
