package com.jokesapplication.presentation.common.shake

import android.content.Context
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.squareup.seismic.ShakeDetector

class ShakeDetectorDelegate : LifecycleObserver, ShakeDetector.Listener {

    private var detector: ShakeDetector? = null
    private var sensorManager: SensorManager? = null
    private var listener: ShakeListener? = null

    fun init(context: Context, owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
        if (detector == null) {
            detector = ShakeDetector(this).apply {
                setSensitivity(ShakeDetector.SENSITIVITY_MEDIUM)
            }
            sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        detector?.apply {
            start(sensorManager)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        detector?.apply {
            stop()
        }
        this.listener = null
    }

    override fun hearShake() {
        Log.d("ShakeDetectorDelegate", "shake")
        listener?.onShake()
    }

    interface ShakeListener {
        fun onShake()
    }

    fun setShakeListener(listener: ShakeListener) {
        this.listener = listener
    }


}
