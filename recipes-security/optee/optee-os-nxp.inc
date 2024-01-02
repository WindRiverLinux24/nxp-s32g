require optee-nxp.inc

MAJ_VER:nxp-s32g = "${@oe.utils.trim_version("${PV}", 2)}"

SRCREV:nxp-s32g = "c64c4b752dea83c9cbecaf8a6d212e12d13b9813"

URL:nxp-s32g ?= "git://github.com/nxp-auto-linux/optee_os;protocol=https"
BRANCH:nxp-s32g ?= "${RELEASE_BASE}-${MAJ_VER}"
SRC_URI:nxp-s32g = "\
    ${URL};branch=${BRANCH} \
"
