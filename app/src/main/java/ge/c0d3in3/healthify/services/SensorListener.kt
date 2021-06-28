package ge.c0d3in3.healthify.services

import android.app.*
import android.content.*
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.extensions.getToday
import ge.c0d3in3.healthify.extensions.getTomorrow
import ge.c0d3in3.healthify.model.StepData
import ge.c0d3in3.healthify.repository.StepCounterRepository
import ge.c0d3in3.healthify.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


/**
 * Background service which keeps the step-sensor listener alive to always get
 * the number of steps since boot.
 *
 *
 * This service won't be needed any more if there is a way to read the
 * step-value without waiting for a sensor event
 */
class SensorListener : Service(), SensorEventListener {
    private val shutdownReceiver: BroadcastReceiver = ShutdownRecevier()
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    private lateinit var preferences: SharedPreferences
    private val stepCounterRepository by inject<StepCounterRepository>()
    private val userRepository by inject<UserRepository>()

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onSensorChanged(event: SensorEvent) {
        if (event.values[0] > Int.MAX_VALUE) return
        else {
            if (lastSaveSteps == -1)
                lastSaveSteps = event.values[0].toInt()
            steps = event.values[0].toInt()
            updateIfNecessary()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun updateIfNecessary() {
        if (steps > lastSaveSteps + SAVE_OFFSET_STEPS ||
            steps > 0 && System.currentTimeMillis() > lastSaveTime + SAVE_OFFSET_TIME
        ) {
            scope.launch(Dispatchers.Default) {
                if(!userRepository.isUserLoggedIn()) return@launch
                val stepsData = stepCounterRepository.getAllSteps().toMutableList()
                if (stepsData.isEmpty() || stepsData.last().timestamp != getToday()) stepsData.add(
                    StepData(getToday(), 0, targetStep = stepCounterRepository.getCurrentTargetStep()!!)
                )
                if(stepsData.last().steps >= stepsData.last().targetStep) return@launch
                stepsData.last().steps += steps - lastSaveSteps
                stepCounterRepository.saveSteps(stepsData)
                lastSaveSteps = steps
                lastSaveTime = System.currentTimeMillis()
            }
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        startForeground()
        reRegisterSensor()
        registerBroadcastReceiver()

        preferences = applicationContext.getSharedPreferences("app", Context.MODE_PRIVATE)
        lastSaveSteps = preferences.getInt("steps", -1)
        lastSaveTime = preferences.getLong("lastSaveTime", 0)
        // restart service every hour to save the current step count
        val nextUpdate =
            getTomorrow().coerceAtMost(System.currentTimeMillis() + AlarmManager.INTERVAL_HOUR)
        val am =
            applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pi = PendingIntent
            .getService(
                applicationContext,
                2,
                Intent(this, SensorListener::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        if (Build.VERSION.SDK_INT >= 23) {
            am.setAndAllowWhileIdle(AlarmManager.RTC, nextUpdate, pi)
        } else {
            am[AlarmManager.RTC, nextUpdate] = pi
        }
        return START_STICKY
    }

    private fun startForeground() {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                createNotificationChannel("healthify_service", "Healthify Step Track")
            else
                ""

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(PRIORITY_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(101, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        super.onTaskRemoved(rootIntent)
        // Restart service in 500 ms
        (getSystemService(Context.ALARM_SERVICE) as AlarmManager)[AlarmManager.RTC, System.currentTimeMillis() + 500] =
            PendingIntent
                .getService(
                    this,
                    3,
                    Intent(this, SensorListener::class.java),
                    0
                )
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        try {
            val sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sm.unregisterListener(this)
            preferences.edit().putInt("steps", lastSaveSteps).apply()
            preferences.edit().putLong("lastSaveTime", lastSaveTime).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun registerBroadcastReceiver() {
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SHUTDOWN)
        registerReceiver(shutdownReceiver, filter)
    }

    private fun reRegisterSensor() {
        val sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        try {
            sm.unregisterListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // enable batching with delay of max 5 min
        sm.registerListener(
            this,
            sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
            SensorManager.SENSOR_DELAY_NORMAL,
            (5 * MICROSECONDS_IN_ONE_MINUTE).toInt()
        )
    }

    companion object {
        private const val MICROSECONDS_IN_ONE_MINUTE: Long = 60000000
        private const val SAVE_OFFSET_TIME = AlarmManager.INTERVAL_HOUR
        private const val SAVE_OFFSET_STEPS = 500
        private var steps = 0
        private var lastSaveSteps = -1
        private var lastSaveTime: Long = 0
    }
}