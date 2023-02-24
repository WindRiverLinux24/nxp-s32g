SRC_URI:append = " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'm7_boot', 'file://0001-hse-pkcs-secboot-add-code-to-support-m7-secure-boot.patch', '', d)} \
"
