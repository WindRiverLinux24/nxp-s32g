require recipes-bsp/u-boot/u-boot-tools.inc
require u-boot-s32.inc

DESCRIPTION = "U-Boot bootloader tools provided by NXP with focus on S32 chipsets"

DEPENDS += "gnutls util-linux"

PROVIDES:class-native += "u-boot-tools-native"

DEPENDS += "${@ 'python3-native python-fdt-native' if d.getVar('SCMI_DTB_NODE_CHANGE') == "true" else ''}"

do_install:append() {
   # Switch from the SIUL2 nodes to the SCMI ones
   install -m 0755 ${S}/tools/nxp/scmi_dtb_node_change.py  ${D}${bindir}/scmi_dtb_node_change.py
}

FILES:${PN}-scmi = "${bindir}/scmi_dtb_node_change.py"
PROVIDES += "u-boot-tools-scmi"
PACKAGES += "u-boot-tools-scmi"

PROVIDES:class-native += "u-boot-tools-scmi-native"

RCONFLICTS:${PN} = "u-boot-tools"
RCONFLICTS:${PN}-mkimage = "u-boot-tools-mkimage"
RCONFLICTS:${PN}-mkenvimage = "u-boot-tools-mkenvimage"

do_compile () {
    oe_runmake -C ${S} tools-only_defconfig O=${B}
    oe_runmake -C ${S} cross_tools NO_SDL=1 O=${B}
}

#compile this recipe only when MACHINE is S32G platform
python () {
    target_machines = ['nxp-s32g', 'aptiv-cvc-131']
    current_machine = d.getVar('MACHINE', True)
    if current_machine not in target_machines:
        raise bb.parse.SkipRecipe("This recipe is only for S32G platform ，current machine is: %s, so skip it!" % (current_machine))
}
