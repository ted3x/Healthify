package ge.c0d3in3.healthify.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import ge.c0d3in3.healthify.BuildConfig


class BootReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "BootReceiver"
    }
    override fun onReceive(context: Context, intent: Intent?) {
        if (BuildConfig.DEBUG) Log.d(TAG,"booted")
        val prefs: SharedPreferences =
            context.getSharedPreferences("pedometer", Context.MODE_PRIVATE)
        if (!prefs.getBoolean("correctShutdown", false)) {
            if (BuildConfig.DEBUG) Log.d(TAG,"Incorrect shutdown")
        }
        prefs.edit().remove("correctShutdown").apply()
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(
                Intent(context, SensorListener::class.java)
            )
        } else {
            context.startService(
                Intent(
                    context,
                    SensorListener::class.java
                )
            )
        }
    }
}