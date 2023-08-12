package com.example.kaloriyolcusu.fragments

import android.animation.ObjectAnimator
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.healtapp.db.KcalDatabase
import com.example.healtapp.repo.KcalRepository
import com.example.healtapp.viewmodel.KcalViewModel
import com.example.healtapp.viewmodel.KcalViewModelProviderFactory
import com.example.kaloriyolcusu.R
import com.example.kaloriyolcusu.databinding.FragmentHomeBinding



class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null
    private var stepCount: Int = 0
    private lateinit var viewModel: KcalViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appDatabase = KcalDatabase.getDatabase(requireContext())
        val kcalRepository = KcalRepository(appDatabase)
        val viewModelFactory = KcalViewModelProviderFactory(
            requireActivity().application,
            kcalRepository
        )

        viewModel = ViewModelProvider(this, viewModelFactory).get(KcalViewModel::class.java)


        getAllKcalItemsLiveData()
        deletedbclick()

        binding.kcalButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_kcalFragment)
        }

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null) {
            Toast.makeText(requireContext(), "no step", Toast.LENGTH_LONG).show()
        } else {
            sensorManager.registerListener(
                stepSensorListener,
                stepSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
            Toast.makeText(requireContext(), "yes step", Toast.LENGTH_LONG).show()
        }
    }

    private fun deletedbclick() {
        binding.deleteDb.setOnClickListener {
            deleteAllKcalItems()
            resetProgressBar()

        }
    }

    private fun getAllKcalItemsLiveData() {
        viewModel.getAllKcalItemsLiveData().observe(viewLifecycleOwner) { kcalList ->
            if (kcalList.isNotEmpty()) {
                val firstKcalItem = kcalList[0]
                binding.name.text = firstKcalItem.calories
                binding.fat.text = firstKcalItem.carbohydrateContent
                Log.d("HomeFragment", "Carbohydrates: ${firstKcalItem.carbohydrateContent}")


                val caloriesString = firstKcalItem.calories
                val carbohydratesString = firstKcalItem.carbohydrateContent
                val proteinString = firstKcalItem.proteinContent
                val fatString = firstKcalItem.fatContent

                val calories = caloriesString.toIntOrNull()
                val carbohydrates = carbohydratesString.toIntOrNull()
                val protein = proteinString.toIntOrNull()
                val fat = fatString.toIntOrNull()

                if (calories != null) {
                    val maxProgress = 1000
                    binding.progressBar2.max = maxProgress
                    updateProgressBar(calories, maxProgress, binding.progressBar2)
                }
                else if (carbohydrates != null) {
                    val maxProgress = 1000
                    binding.progressBar3.max = maxProgress
                    updateProgressBar(carbohydrates, maxProgress, binding.progressBar3)
                }
                else if (protein != null) {
                    val maxProgress = 1000
                    binding.progressBar4.max = maxProgress
                    updateProgressBar(protein, maxProgress, binding.progressBar4)
                }
                else if (fat != null) {
                    val maxProgress = 1000
                    binding.progressBar5.max = maxProgress
                    updateProgressBar(fat, maxProgress, binding.progressBar5)
                }
                else {
                    Log.e("HomeFragment", "Hata: Değerleri dönüştüremedim.")
                }
            }
        }
    }

    private fun updateProgressBar(value: Int, maxProgress: Int, progressBar: ProgressBar) {
        val currentProgress = progressBar.progress

        val newProgress = currentProgress + value
        if (newProgress <= maxProgress) {
            progressBar.progress = newProgress
        } else {
            progressBar.progress = maxProgress
        }
    }



    private fun resetProgressBar() {
        binding.progressBar2.progress = 0
    }
    private fun deleteAllKcalItems() {
        viewModel.deleteAllKcalItems()
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            stepSensorListener,
            stepSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(stepSensorListener)
    }

    private val stepSensorListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // Do nothing
        }

        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                val steps = event.values[0]
                updateStepCount(steps.toInt())
            }
        }
    }

    private fun updateStepCount(currentStepCount: Int) {
        if (stepCount == 0) {
            stepCount = currentStepCount
        } else {
            val newSteps = currentStepCount - stepCount
            stepCount = currentStepCount
            stepCount += newSteps
            binding.stepCount.text = stepCount.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}