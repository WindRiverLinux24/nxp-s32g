# ATF repository
URL ?= "git://github.com/nxp-auto-linux/arm-trusted-firmware.git;protocol=https"
BRANCH ?= "release/bsp43.0-2.10.7"
SRC_URI = "${URL};branch=${BRANCH}"
SRCREV ?= "7e70e6bd69b14be23dcc42bf20c7c19bf7c4e0af"
SRC_URI[sha256sum] = "15d263b62089b46375effede12a1917cd7b267b93dd97c68fd5ddbd1dddede07"
