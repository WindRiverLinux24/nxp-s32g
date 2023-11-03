
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI:append:nxp-s32g = " \
    file://0001-canfdtest-Relax-the-order-of-incoming-messages.patch \
"

