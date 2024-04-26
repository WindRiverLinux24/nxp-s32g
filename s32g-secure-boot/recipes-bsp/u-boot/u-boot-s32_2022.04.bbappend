SRC_URI:append = " \
    file://0001-Revert-hse-secboot-remove-unused-u-boot-secboot-code.patch \
    file://0002-u-boot-secboot-correct-the-secure-boot-config.patch \
    file://0003-s32-hse-support-secure-boot-feature-on-both-S32G2-an.patch \
    file://0001-s32g-hse-reconstruct-the-code-used-to-enable-secure-.patch \
    file://0001-s32g-hse-support-M7-secure-boot-feature.patch \
    file://0001-s32g-hse-support-NXP-parallel-secure-boot-feature.patch \
    file://0001-s32g-hse-support-Aptiv-autosar-secure-boot-feature.patch \
    file://0001-s32g-hse-support-Aptiv-parallel-secure-boot-feature.patch \
    file://0001-s32g-hse-improve-the-code-of-enable-secure-boot-comm.patch \
    file://0001-s32g-hse-improve-code-of-secure-boot-enable-command-.patch \
    file://0001-s32g-hse-get-the-reasonable-length-of-image-data.patch \
"

python() {
    if d.getVar('HSE_SEC_ENABLED') == '0':
        bb.fatal("Please set HSE firmware path for secure boot feature firstly, and then build again.")
}
