
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:nxp-s32g2xx = " \
	file://0001-rngd-jitter-drop-affinity-settings-for-entropy-task-.patch \
"
