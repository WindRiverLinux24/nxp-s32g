require optee-nxp.inc

MAJ_VER:nxp-s32g = "${@oe.utils.trim_version("${PV}", 2)}"

SRCREV:nxp-s32g = "1ef6b4cf34e448bd1c134f30509ecf1f9b5d36bd"

URL:nxp-s32g ?= "git://github.com/nxp-auto-linux/optee_os;protocol=https"
BRANCH:nxp-s32g ?= "${RELEASE_BASE}-${MAJ_VER}"
SRC_URI:nxp-s32g = "\
    ${URL};branch=${BRANCH} \
    file://0002-core-Define-section-attributes-for-clang.patch \
    file://0003-optee-enable-clang-support.patch \
    file://0004-core-link-add-no-warn-rwx-segments.patch \
"
