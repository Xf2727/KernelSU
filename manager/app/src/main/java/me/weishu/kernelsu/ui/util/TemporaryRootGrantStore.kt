package me.weishu.kernelsu.ui.util

import android.content.Context
import me.weishu.kernelsu.Natives
import me.weishu.kernelsu.ksuApp

/** Manager-side expiry for temporary grants using the real profile ioctl. */
object TemporaryRootGrantStore {
    private const val PREFS = "temporary_root_grants"

    fun grant(uid: Int, packageName: String, durationMs: Long) {
        ksuApp.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
            .putString(uid.toString(), "$packageName:${System.currentTimeMillis() + durationMs}")
            .apply()
    }

    fun revokeExpired() {
        val prefs = ksuApp.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val now = System.currentTimeMillis()
        prefs.all.forEach { (uidKey, raw) ->
            val parts = (raw as? String)?.split(':', limit = 2) ?: return@forEach
            val uid = uidKey.toIntOrNull() ?: return@forEach
            val expires = parts.getOrNull(1)?.toLongOrNull() ?: 0L
            if (expires <= now) {
                val current = Natives.getAppProfile(parts[0], uid)
                if (current.allowSu) Natives.setAppProfile(current.copy(allowSu = false))
                prefs.edit().remove(uidKey).apply()
            }
        }
    }
}
