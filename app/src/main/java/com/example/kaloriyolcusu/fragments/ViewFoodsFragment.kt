package com.example.kaloriyolcusu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healtapp.db.KcalDatabase
import com.example.healtapp.repo.KcalRepository
import com.example.healtapp.viewmodel.KcalViewModel
import com.example.healtapp.viewmodel.KcalViewModelProviderFactory
import com.example.kaloriyolcusu.R
import com.example.kaloriyolcusu.adapter.FoodAdapter


class ViewFoodsFragment : Fragment() {

    private lateinit var kcalAdapter: FoodAdapter
    private lateinit var viewModel: KcalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_foods, container, false)
        val appDatabase = KcalDatabase.getDatabase(requireContext())
        val kcalRepository = KcalRepository(appDatabase)
        val viewModelFactory = KcalViewModelProviderFactory(
            requireActivity().application,
            kcalRepository
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(KcalViewModel::class.java)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_SearchFragment)
        kcalAdapter = FoodAdapter(requireContext(),viewModel)
        recyclerView.adapter = kcalAdapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)


        viewModel.getAllKcalItemsLiveData().observe(viewLifecycleOwner, { kcalList ->
            kcalAdapter.kcalList = kcalList
            kcalAdapter.notifyDataSetChanged()
        })

        return view
    }
}