DESCRIPTION = "libFCI networking acceleration library"
HOMEPAGE = "https://source.codeaurora.org/external/autobsps32/extra/pfeng"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE-GPL2.txt;md5=5dcdfe25f21119aa5435eab9d0256af7"

PR = "r0"

URL ?= "git://source.codeaurora.org/external/autobsps32/extra/pfeng;protocol=https;branch=master"
SRC_URI = "${URL}"
SRCREV = "0669f53e2cc0b3c133406e3db2ae72f5b3934e5f"

PATCHTOOL = "git"
S = "${WORKDIR}/git"
MDIR = "${S}/sw/xfci/libfci"
LIBBUILDDIR = "${S}/sw/xfci/libfci/build/${TARGET_SYS}-release"

CFLAGS:prepend = "-I${S} "

PACKAGES = "${PN} ${PN}-dev ${PN}-staticdev"
RDEPENDS_${PN} = "pfe"

do_compile() {
	cd ${MDIR}
	${MAKE} TARGET_OS=LINUX PLATFORM=${TARGET_SYS} ARCH=${PACKAGE_ARCH}  linux
}

do_install() {
	install -d ${D}${libdir}
	install -m 0644 ${LIBBUILDDIR}/libfci.a ${D}${libdir}
}

ALLOW_EMPTY:${PN} = "1"
ALLOW_EMPTY:${PN}-dev = "1"

FILES:${PN}-dev = "${includedir}"
FILES:${PN}-staticdev = "${libdir}/libfci.a"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"

