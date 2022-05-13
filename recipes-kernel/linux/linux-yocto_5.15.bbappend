require linux-yocto-nxp-s32g.inc

KBRANCH:nxp-s32g  = "v5.15/standard/nxp-sdk-5.10/nxp-s32g"

LINUX_VERSION:nxp-s32g ?= "5.15.x"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append:nxp-s32g = " \
	file://0001-Revert-modpost-turn-missing-MODULE_LICENSE-into-erro.patch \
	file://0001-dts-fsl-s32g-pfe-modify-BMU2-reserved-memory-node-to.patch \
"
