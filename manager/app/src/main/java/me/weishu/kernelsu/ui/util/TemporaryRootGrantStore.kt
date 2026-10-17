package me.weishu.kernelsu.ui.util

import android.content.Context
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import me.weishu.kernelsu.Natives
import me.weishu.kernelsu.ksuApp

/** Manager-side expiry for temporary grants using the real profile ioctl. */
object TemporaryRootGrantStore {
    private const val PREFS = "temporary_root_grants"

    fun grant(uid: Int, packageName: String, durationMs: Long) {
        val expiresAt = System.currentTimeMillis() + durationMs
        ksuApp.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
            .putString(uid.toString(), "$packageName:$expiresAt")
            .apply()
        val alarm = ksuApp.getSystemService(AlarmManager::class.java)
        val intent = Intent(ksuApp, TemporaryRootGrantReceiver::class.java).apply {
            putExtra(EXTRA_UID, uid)
        }
        val pending = PendingIntent.getBroadcast(
            ksuApp, uid, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarm?.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, expiresAt, pending)
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
                cancelAlarm(uid)
            }
        }
    }

    fun revoke(uid: Int) {
        val prefs = ksuApp.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val raw = prefs.getString(uid.toString(), null) ?: return
        val packageName = raw.substringBefore(':')
        val current = Natives.getAppProfile(packageName, uid)
        if (current.allowSu) Natives.setAppProfile(current.copy(allowSu = false))
        prefs.edit().remove(uid.toString()).apply()
        cancelAlarm(uid)
    }

    private fun cancelAlarm(uid: Int) {
        val intent = Intent(ksuApp, TemporaryRootGrantReceiver::class.java)
        PendingIntent.getBroadcast(ksuApp, uid, intent, PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE)
            ?.let { ksuApp.getSystemService(AlarmManager::class.java)?.cancel(it) }
    }

    const val EXTRA_UID = "uid"
}
