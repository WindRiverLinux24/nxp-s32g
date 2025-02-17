do_install_ptest:append:nxp-s32g () {
	cp -fR ${S}/tests/data_files ${D}${PTEST_PATH}/tests/
}
