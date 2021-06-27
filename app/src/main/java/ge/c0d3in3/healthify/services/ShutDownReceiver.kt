package ge.c0d3in3.healthify.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.room.Database
import ge.c0d3in3.healthify.BuildConfig


class ShutdownRecevier : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (BuildConfig.DEBUG) Log.d("ShutdownRecevier","shutting down")
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(
                Intent(context, ShutdownRecevier::class.java)
            )
        } else {
            context.startService(
                Intent(
                    context,
                    ShutdownRecevier::class.java
                )
            )
        }
        // if the user used a root script for shutdown, the DEVICE_SHUTDOWN
        // broadcast might not be send. Therefore, the app will check this
        // setting on the next boot and displays an error message if it's not
        // set to true
        context.getSharedPreferences("pedometer", Context.MODE_PRIVATE).edit()
            .putBoolean("correctShutdown", true).apply()
    }
}