require optee-nxp.inc

do_install:append:nxp-s32g () {
    mkdir -p ${D}${nonarch_libdir}/tee-supplicant/plugins
    install -D -p -m0444 ${B}/supp_plugin/*.plugin ${D}${nonarch_libdir}/tee-supplicant/plugins/
}

FILES:${PN} += "${nonarch_libdir}/tee-supplicant/plugins/"
