SUMMARY = "OpenEmbedded Image Creator (wic) standalone CLI"
HOMEPAGE = "https://git.yoctoproject.org/wic"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4ee23c52855c222cba72583d301d2338"

SRC_URI = "git://git.yoctoproject.org/wic.git;branch=master;protocol=https;tag=v${PV}"
SRCREV = "6c66da65bf2aac618092811c13792dcbd453d140"

CVE_PRODUCT = "yoctoproject:wic"

inherit python_hatchling

RDEPENDS:${PN} += " \
    python3-core \
    python3-json \
    python3-logging \
    python3-misc \
    "

# wic shells out to these host-side tools when it runs, so they must be
# installed alongside it.
RDEPENDS:${PN} += " \
    parted \
    gptfdisk \
    dosfstools \
    mtools \
    bmaptool \
    btrfs-tools \
    squashfs-tools \
    e2fsprogs \
    e2fsprogs-resize2fs \
    util-linux \
    tar \
    erofs-utils \
"

# grub (grub-mkimage) assembles EFI boot images; it is only compatible
# with, and only used by wic on, x86 and aarch64, so gate it to those
# hosts. In particular grub is not compatible with arm hard-float, so an
# unconditional dependency makes wic unbuildable there. syslinux is only
# available on x86: its installer provides the isohybrid helper wic runs,
# syslinux-misc carries ldlinux.sys/isohdpfx.bin/ldlinux.c32 and
# syslinux-isolinux carries isolinux.bin, all of which wic copies into a
# hybrid ISO.
RDEPENDS:${PN}:append:x86 = " grub syslinux syslinux-misc syslinux-isolinux"
RDEPENDS:${PN}:append:x86-64 = " grub syslinux syslinux-misc syslinux-isolinux"
RDEPENDS:${PN}:append:x86-x32 = " grub syslinux syslinux-misc syslinux-isolinux"
RDEPENDS:${PN}:append:aarch64 = " grub"

BBCLASSEXTEND = "native nativesdk"
