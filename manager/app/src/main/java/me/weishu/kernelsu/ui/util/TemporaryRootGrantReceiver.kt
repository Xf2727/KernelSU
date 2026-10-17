package me.weishu.kernelsu.ui.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/** Revokes an expiring profile while the Manager UI is not running. */
class TemporaryRootGrantReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val uid = intent.getIntExtra(TemporaryRootGrantStore.EXTRA_UID, -1)
        if (uid >= 0) TemporaryRootGrantStore.revoke(uid)
    }
}
