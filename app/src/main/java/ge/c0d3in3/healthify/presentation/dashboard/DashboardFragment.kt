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
import com.bumptech.glide.Glide
import ge.c0d3in3.components.DashboardItemView
import ge.c0d3in3.components.formatToWeight
import ge.c0d3in3.components.sleep_tracker.SleepTrackerView
import ge.c0d3in3.components.step_tracker.StepTrackerView
import ge.c0d3in3.components.water_tracker.WaterTrackerView
import ge.c0d3in3.components.weight_tracker.WeightTrackerView
import ge.c0d3in3.healthify.R
import ge.c0d3in3.healthify.base.BaseFragment
import ge.c0d3in3.healthify.databinding.DashboardFragmentBinding
import ge.c0d3in3.healthify.extensions.calculateBMI
import ge.c0d3in3.healthify.extensions.calculateBurnedCalories
import ge.c0d3in3.healthify.model.User
import ge.c0d3in3.healthify.presentation.dashboard.di.dashboardModule
import ge.c0d3in3.healthify.services.SensorListener
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.dsl.module

class DashboardFragment :
    BaseFragment<DashboardFragmentBinding, DashboardViewModel>(DashboardFragmentBinding::inflate),
    SensorEventListener {
    override val module = dashboardModule
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

        vm.user.observe {
            binding.userNameTv.text = "${it.firstName} ${it.lastName}"
            binding.userAgeTv.text = "Age: ${it.age}"
            binding.userHeightTv.text = "Height: ${it.height.toDouble() / 100}m"
            binding.userWeightTv.text = "Weight: ${it.weight.formatToWeight()}kg"
            val bmi = calculateBMI(requireContext(), it.weight, it.height)
            binding.userBMITv.text = "BMI: ${bmi.bmi?.formatToWeight()} [${bmi.text}]"
            Glide.with(requireContext()).load(it.profilePicture).circleCrop().into(binding.userIv)
            setUpTrackers(it)
        }

        vm.todaySteps.observe {
            binding.stepTrackerView.setSteps(it.toDouble())
            binding.weighTrackerView.setBurnedCalories(calculateBurnedCalories(it))
        }

        vm.stepData.observe {
            binding.waterTrackerView.setSteps(it.water.toDouble())
            binding.sleepTrackerView.setSteps(it.sleep.toDouble())
        }

        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACTIVITY_RECOGNITION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 0
            )
        }

        binding.profileTv.setOnClickListener {
            vm.navigateToProfile()
        }
    }

    private fun setUpTrackers(user: User) {
        val currentStepData = if(user.steps.isNotEmpty()) user.steps.last() else vm.getDefaultStepData()
        val target = if (user.steps.isEmpty()) user.targetStep
        else user.steps.last().targetStep
        setUpStepTracker(binding.stepTrackerView, target.toDouble())

        setUpWeightTracker(binding.weighTrackerView, user.weight, user.targetWeight)

        setUpWaterTracker(
            binding.waterTrackerView,
            currentStepData.water.toDouble(),
            user.targetWater.toDouble()
        )

        setUpSleepTracker(
            binding.sleepTrackerView,
            currentStepData.sleep.toDouble(),
            currentStepData.targetSleep.toDouble()
        )
    }

    private fun setUpStepTracker(tracker: StepTrackerView, target: Double) {
        tracker.setTarget(target)
        tracker.onCompletion { completed ->
            vm.isCompleted = completed
            if (completed)
                vm.saveSteps()
        }
    }

    private fun setUpWeightTracker(
        tracker: WeightTrackerView,
        currentWeight: Double,
        target: Double
    ) {
        tracker.setUp(currentWeight, target)
        tracker.setOnWeightAddListener {
            vm.addWeight(it)
        }
    }

    private fun setUpWaterTracker(tracker: WaterTrackerView, currentWater: Double, target: Double) {
        tracker.setUp(currentWater, target)
        tracker.setOnDrinkListener {
            vm.drinkWater()
        }
    }

    private fun setUpSleepTracker(
        tracker: SleepTrackerView,
        currentSleepHours: Double,
        target: Double
    ) {
        tracker.setUp(currentSleepHours, target)
        tracker.setOnSleepAddListener {
            vm.addSleep(it)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (!vm.isCompleted) {
            if (event!!.values[0] > Int.MAX_VALUE || event.values[0] == 0.0F) {
                return
            }
            if (vm.lastStep == 0) vm.lastStep = event.values[0].toInt()
            vm.onStepChanged(event.values[0])
        }
    }

    override fun onPause() {
        try {
            val sm =
                (requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager)
            sm.unregisterListener(this)
            vm.saveSteps()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onPause()
    }

    private fun DashboardItemView.setUp(steps: Double, target: Double) {
        this.setTarget(target)
        this.setSteps(steps)
    }
}