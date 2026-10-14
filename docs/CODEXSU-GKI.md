# CodexSU GKI build

This workflow builds an experimental Android 14 / Linux 6.1 ARM64 GKI
with CodexSU built in (`CONFIG_KSU=y`). It uses the checked-out CodexSU
source, application ID `codexsu.me`, and the existing certificate checks.
No signing private key or Actions signing secret is required for the kernel.
APK signing certificates and boot AVB signing keys serve different purposes.

## Upload and run

1. Commit `.github/workflows/codexsu-gki.yml` and this document on `codexsu`.
2. Push the branch with `git push origin codexsu` or GitHub Desktop's Push origin.
3. The workflow must exist on the repository's default branch to show the
   Run workflow button. If desired, set your fork's default branch to `codexsu`
   in Settings (do not change the upstream repository).
4. Open Actions > Build codexsu GKI > Run workflow, select `codexsu`.
5. Choose an AOSP kernel manifest branch/tag. The default
   `common-android14-6.1-2025-01` follows the reference workflow's 6.1.118
   monthly source selection; this does not prove compatibility with a phone.
6. Download `codexsu-gki-android14-6.1` from the successful run's Artifacts.
   The artifact contains Image and an actual AnyKernel3 ZIP, plus the pinned
   manifest, CodexSU commit and AnyKernel3 commit. Extract the outer Actions
   archive to obtain the inner flashable ZIP. No boot image is generated.

The pipeline references Xf2727/GKI_KernelSU_SUSFS's monthly manifest,
Kleaf defconfig-fragment and WildKernels/AnyKernel3 gki-2.0 packaging approach.
It integrates this repository's CodexSU source, without SUSFS, KPM or OnePlus
device-specific patches. Source synchronization retries transient errors and
compile logs are uploaded even on failure. A removed monthly common branch
may require selecting a supported manifest/tag; it is not silently replaced.

## Before installing

A MIX Fold4 reporting `6.1.118-android14-11` is not fully identified by
`android14-6.1`. Match the KMI generation (`11`), vendor module requirements,
security patch / rollback constraints, boot header, compression and AVB
requirements with the stock firmware. A newer AOSP branch can be incompatible.
Prefer a matching, immutable manifest tag after inspecting the stock boot.

GKI replaces the kernel in **boot**, not the init_boot LKM payload. Do not
flash this build to init_boot. Keep stock boot/init_boot and working recovery
available. Remove the previously installed LKM from the boot chain as part
of a planned migration: built-in KernelSU and a second loaded LKM must not
be combined. Do not assume an AOSP-generated boot image preserves the
phone's stock boot metadata. Repack or select the image only after inspecting
the original firmware images.

This workflow has not yet been validated by a successful Actions run or on
the device. A successful compile is not a device compatibility test.
