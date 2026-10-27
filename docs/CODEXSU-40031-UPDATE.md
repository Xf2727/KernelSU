# CodexSU 40031 update

This source update includes all 116 added/changed files from the supplied codexsu archive.
Package codexsu.me and the existing signing certificate remain unchanged.

## Build and deploy

1. In GitHub Desktop, fetch and pull the codexsu branch before pushing the local integration commit. Resolve any conflicts without discarding the integration.
2. Push origin. Run the CodexSU LKM workflow and wait for all requested KMI artifacts to succeed.
3. Run Build Manager for codexsu using the new LKM artifacts. Both kernel and Manager version codes are 40031.
4. Install the APK using the existing signing key. Back up stock boot/init_boot before updating the device LKM using the supported installation flow.
5. Validate UAPI 6, modules, scripts, preinit status, search and back navigation on the device.

## Included changes

- Kernel UAPI 6, batch kernel symbol lookup and module preinit runner.
- ksud/ksuinit preinit support, boot patch and module lifecycle updates.
- Material search window navigation bridge and other upstream Manager fixes.
- DDK builds install target kernel UAPI headers so preinit is not silently omitted for missing headers.

## Authorization

Temporary Root authorization has been removed. Use the existing Root allow/deny switch and App Profile templates. Previously granted applications remain governed by their stored kernel profiles; review the Superuser page and manually revoke any grants you no longer want.

## Verification status

Source checks are separate from successful Android/kernel builds. Run the workflows and device checks above before relying on this release. No signing private key is included.
