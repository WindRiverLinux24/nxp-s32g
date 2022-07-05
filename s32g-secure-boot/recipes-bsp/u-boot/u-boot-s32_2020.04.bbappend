FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:nxp-s32g = " \
    file://0001-configs-s32g2xx-enable-CONFIG_FIT_SIGNATURE-for-secu.patch \
"

python() {
    if d.getVar('HSE_SEC_ENABLED') == '0':
        bb.fatal("Please set HSE firmware path for secure boot feature firstly, and then build again.")
}
