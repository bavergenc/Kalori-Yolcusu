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
import com.example.healtapp.model.Kcal
import com.example.healtapp.repo.KcalRepository
import com.example.healtapp.viewmodel.KcalViewModel
import com.example.healtapp.viewmodel.KcalViewModelProviderFactory
import com.example.kaloriyolcusu.R
import com.example.kaloriyolcusu.adapter.FoodAdapter
import com.example.kaloriyolcusu.databinding.FragmentHomeBinding
import java.text.DecimalFormat


class HomeFragment : Fragment() , FoodAdapter.OnDeleteClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null
    private var stepCount: Int = 0
    private lateinit var viewModel: KcalViewModel
    private lateinit var foodAdapter: FoodAdapter
    private var waterConsumed = 0

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

        foodAdapter = FoodAdapter(requireContext(),viewModel)
        foodAdapter.setOnDeleteClickListener(this) // Listener'ı set ediyoruz

        getAllKcalItemsLiveData()
        OpenViewFoodsFragment()
        binding.waterprogress.max = 2000

        binding.addWater.setOnClickListener {
            increaseWaterConsumed()
        }

        binding.kcalButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_kcalFragment)
        }
        updateWaterText()


    }

    private fun OpenViewFoodsFragment() {
        binding.viewFoods.setOnClickListener {
            // removeIcon görünürlüğünü değiştirin
            foodAdapter.toggleRemoveIconVisibility()
            findNavController().navigate(R.id.action_homeFragment_to_viewFoodsFragment)

        }

        // Diğer işlemler
    }

    private fun getAllKcalItemsLiveData() {
        viewModel.getAllKcalItemsLiveData().observe(viewLifecycleOwner) { kcalList ->
            if (kcalList.isNotEmpty()) {
                var totalCalories = 0
                var totalCarbohydrates = 0.0
                var totalProtein = 0.0
                var totalFat = 0.0

                for (kcalItem in kcalList) {
                    totalCalories += kcalItem.calories.toIntOrNull() ?: 0
                    totalCarbohydrates += kcalItem.carbohydrateContent.toDoubleOrNull() ?: 0.0
                    totalProtein += kcalItem.proteinContent.toDoubleOrNull() ?: 0.0
                    totalFat += kcalItem.fatContent.toDoubleOrNull() ?: 0.0
                }

                val decimalFormat = DecimalFormat("#.#")

                binding.calori.text = decimalFormat.format(totalCalories) + " / 2000"
                binding.carb.text = decimalFormat.format(totalCarbohydrates) + " / 97"
                binding.protein.text = decimalFormat.format(totalProtein) + " / 97"
                binding.fat.text = decimalFormat.format(totalFat) + " / 57"

                val maxProgressCalorie = 2000
                val maxProgressCarb = 97
                val maxProgressProtein = 97
                val maxProgressFat = 57

                binding.caloriprogress.max = maxProgressCalorie
                binding.carbprogressBar.max = maxProgressCarb
                binding.proteinProgressBar.max = maxProgressProtein
                binding.fatprogress.max = maxProgressFat

                updateProgressBar(totalCalories, maxProgressCalorie, binding.caloriprogress)
                updateProgressBar(totalCarbohydrates, maxProgressCarb, binding.carbprogressBar)
                updateProgressBar(totalProtein, maxProgressProtein, binding.proteinProgressBar)
                updateProgressBar(totalFat, maxProgressFat, binding.fatprogress)
            } else {
                resetProgressBars()
            }
    }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("waterConsumed", waterConsumed)
    }

    private fun updateWaterText() {
        binding.waterText.text = "$waterConsumed / 2000"
    }

    private fun resetProgressBars() {
        binding.caloriprogress.progress = 0
        binding.carbprogressBar.progress = 0
        binding.proteinProgressBar.progress = 0
        binding.fatprogress.progress = 0
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

    private fun updateProgressBar(value: Double, maxProgress: Int, progressBar: ProgressBar) {
        val currentProgress = progressBar.progress

        val newProgress = currentProgress + value.toInt()
        if (newProgress <= maxProgress) {
            progressBar.progress = newProgress
        } else {
            progressBar.progress = maxProgress
        }
    }



    private fun increaseWaterConsumed() {
        if (waterConsumed < 2000) {
            waterConsumed += 200
            binding.waterprogress.progress = waterConsumed
            updateWaterText()
        }
    }
    override fun onDeleteClicked(item: Kcal) {
        viewModel.getAllKcalItemsLiveData().observe(viewLifecycleOwner) { kcalList ->

        viewModel.deleteKcalItem(item)
}

    }
}




