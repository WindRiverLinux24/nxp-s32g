require ${@bb.utils.contains('MACHINE', 'nxp-s32g', 'linux-yocto-nxp-s32g.inc', '', d)}

KBRANCH:nxp-s32g  = "v6.6/standard/preempt-rt/nxp-sdk-6.6/nxp-s32g"
