package com.whitestudios.shakeaction.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.widget.Toast
import com.whitestudios.shakeaction.R
import com.whitestudios.shakeaction.shake.ShakeDetector
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    val context: Context = this@HomeActivity

    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null
    private var mShakeDetector: ShakeDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager!!
            .getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mShakeDetector = ShakeDetector()
        mShakeDetector?.setOnShakeListener(object :
            ShakeDetector.OnShakeListener {

            override fun onShake(count: Int) {
                tvShake.text = "Se detectó el movimiento"
                Toast.makeText(context, "Sacudido!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mSensorManager?.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        mSensorManager?.unregisterListener(mShakeDetector)
        super.onPause()
    }
}
