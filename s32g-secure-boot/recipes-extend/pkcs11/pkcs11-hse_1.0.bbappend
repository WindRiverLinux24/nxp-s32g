FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    ${@bb.utils.contains('S32G_FEATURES', 'm7_boot', 'file://0001-hse-pkcs-secboot-add-code-to-support-m7-secure-boot.patch', '', d)} \
"