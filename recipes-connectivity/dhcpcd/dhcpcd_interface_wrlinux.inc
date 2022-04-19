update_dhcpcd_interface:nxp-s32g() {
    if [ -e ${D}/etc/default/dhcpcd ]; then
        echo "INTERFACES=eth0 pfe1 pfe2" > ${D}/etc/default/dhcpcd
    fi
}

UPDATE_DHCPCD_INTERFACE_FUNC ?= ""
UPDATE_DHCPCD_INTERFACE_FUNC:nxp-s32g = "update_dhcpcd_interface"
do_install[postfuncs] += "${UPDATE_DHCPCD_INTERFACE_FUNC}"
