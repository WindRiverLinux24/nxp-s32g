BL2_HANDLE ?= "0x010700"
BL31_HANDLE ?= "${BL2_HANDLE}"
BL32_HANDLE ?= "${BL2_HANDLE}"
BL33_HANDLE ?= "${BL2_HANDLE}"

RSA_PRIV_BL2 ?= "bl2_rsa2048_private.pem"
RSA_PRIV_BL31 ?= "${RSA_PRIV_BL2}"
RSA_PRIV_BL32 ?= "${RSA_PRIV_BL2}"
RSA_PRIV_BL33 ?= "${RSA_PRIV_BL2}"

RSA_PUB_BL2 ?= "bl2_rsa2048_public.pem"
RSA_PUB_BL31 ?= "bl31_rsa2048_public.pem"
RSA_PUB_BL32 ?= "bl32_rsa2048_public.pem"
RSA_PUB_BL33 ?= "bl33_rsa2048_public.pem"

SECBOOT_KEYS_INSTALL_PATH ?= "/boot/secboot/"

