package ric.silva.revsample

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var scheduledExecutorService: ScheduledExecutorService
    private var scheduledGetRates: ScheduledFuture<*>? = null
    private var updateRate: Long = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    fun startScheduleExecutor(callback: () -> Unit){
        Handler().postDelayed({
            Log.e("STARTING", "SCHEDULE")
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
            startScheduleGetRates(callback)
        },500)
    }

    private fun startScheduleGetRates(callback: () -> Unit){
        if (!scheduledExecutorService.isShutdown && scheduledGetRates == null) {
            scheduledGetRates = scheduledExecutorService.schedule({

                callback.invoke()

                scheduledGetRates = null
                startScheduleGetRates(callback)

            }, updateRate, TimeUnit.SECONDS)
        }
    }

    fun stopSchedule() {
        scheduledExecutorService.shutdown()
        scheduledGetRates = null
    }
}