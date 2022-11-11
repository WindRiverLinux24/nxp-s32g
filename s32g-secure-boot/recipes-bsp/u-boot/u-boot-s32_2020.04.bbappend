FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://0001-configs-s32g2xx-enable-CONFIG_FIT_SIGNATURE-for-secu.patch \
    file://0001-arch-mach-s32-extend-the-DTB-size-for-BL33.patch \
    ${@bb.utils.contains('S32G_FEATURES', 'm7_boot', 'file://0001-s32-hse-add-code-to-support-m7-secure-boot.patch', '', d)} \
"

python() {
    if d.getVar('HSE_SEC_ENABLED') == '0':
        bb.fatal("Please set HSE firmware path for secure boot feature firstly, and then build again.")
}
