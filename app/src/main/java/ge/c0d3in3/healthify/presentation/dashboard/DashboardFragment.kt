package ge.c0d3in3.healthify.presentation.dashboard

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ge.c0d3in3.healthify.base.BaseFragment
import ge.c0d3in3.healthify.databinding.DashboardFragmentBinding
import ge.c0d3in3.healthify.services.SensorListener
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.dsl.module

class DashboardFragment :
    BaseFragment<DashboardFragmentBinding, DashboardViewModel>(DashboardFragmentBinding::inflate),
    SensorEventListener {
    override val module = module {
        viewModel {
            DashboardViewModel(
                app = androidApplication(),
                stepCounterRepository = get()
            )
        }
    }
    override val vm by viewModel<DashboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextCompat.startForegroundService(
            requireContext(),
            Intent(requireContext(), SensorListener::class.java)
        )
    }

    override fun onResume() {
        super.onResume()
        val sm =
            requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor =
            sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (sensor == null) {
            with(getDialog()) {
                setTitle("Error!")
                setDialogMessage("No sensor found!")
                setNegativeButtonText("Dismiss")
                setOnNegativeClickListener { requireActivity().finish() }
            }
        } else {
            sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI, 0)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.todaySteps.observe {
            binding.steps.text = it.toString()
        }

        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACTIVITY_RECOGNITION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 0)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.values[0] > Int.MAX_VALUE || event.values[0] == 0.0F) {
            return
        }
        if (vm.lastStep == 0) vm.lastStep = event.values[0].toInt()
        vm.onStepChanged(event.values[0])
    }

    override fun onPause() {
        try {
            val sm =
                (requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager)
            sm.unregisterListener(this)
            vm.saveUser()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onPause()
    }

}