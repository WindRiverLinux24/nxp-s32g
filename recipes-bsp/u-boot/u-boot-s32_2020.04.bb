require recipes-bsp/u-boot/u-boot.inc
require u-boot-s32.inc

DESCRIPTION = "U-boot provided by NXP with focus on S32 chipsets"
PROVIDES += "u-boot"

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS:append = " libgcc virtual/${TARGET_PREFIX}gcc python3 dtc-native bison-native"

inherit nxp-u-boot-localversion

do_compile:append() {
    plats="s32g2xxaevb s32g274ardb2 s32g399ardb3 s32g3xxaevb"
    if [ "${HSE_SEC_ENABLED}" = "1" ]; then
        for plat in $plats; do
            cfgout="${B}/${plat}_defconfig/u-boot-s32.cfgout"
            if [ -e $cfgout ]; then
                if [ $plat = "s32g2xxaevb" ] || [ $plat = "s32g274ardb2" ]; then
                    sed -i 's|${HSE_FW_DEFAULT_NAME}|${HSE_LOCAL_FIRMWARE_DIR}/${HSE_FW_NAME_S32G2}|g' $cfgout
                else
                    sed -i 's|${HSE_FW_DEFAULT_NAME}|${HSE_LOCAL_FIRMWARE_DIR}/${HSE_FW_NAME_S32G3}|g' $cfgout
                fi
            fi
        done
    fi
}

do_deploy:append() {
    unset i j
    for config in ${UBOOT_MACHINE}; do
        i=$(expr $i + 1);
        for type in ${UBOOT_CONFIG}; do
            j=$(expr $j + 1)
            if  [ $j -eq $i ]; then
                install -d ${DEPLOYDIR}/${type}/tools
                install -m 0644 ${B}/${config}/${UBOOT_BINARY} ${DEPLOYDIR}/${type}/${UBOOT_BINARY}
                install -m 0644 ${B}/${config}/${UBOOT_CFGOUT} ${DEPLOYDIR}/${type}/tools/${UBOOT_CFGOUT}
                install -m 0755 ${B}/${config}/tools/mkimage ${DEPLOYDIR}/${type}/tools/mkimage
            fi
        done
        unset j
    done
    unset i
}


COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
