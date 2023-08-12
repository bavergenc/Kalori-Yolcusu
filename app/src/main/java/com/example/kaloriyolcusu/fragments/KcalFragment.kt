package com.example.kaloriyolcusu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healtapp.adapter.AppAdapter
import com.example.healtapp.db.KcalDatabase
import com.example.healtapp.model.Kcal
import com.example.healtapp.repo.KcalRepository
import com.example.healtapp.service.ApiService
import com.example.healtapp.viewmodel.KcalViewModel
import com.example.healtapp.viewmodel.KcalViewModelProviderFactory
import com.example.kaloriyolcusu.R


import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class KcalFragment : Fragment() {


    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val appList = ArrayList<Kcal>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AppAdapter
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var  viewModel: KcalViewModel




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDatabase = KcalDatabase.getDatabase(requireContext()) // Assuming KcalDatabase has a getInstance method

        // Create an instance of KcalRepository using appDatabase
        val kcalRepository = KcalRepository(appDatabase)

        // Pass both the Application and KcalRepository instances to the factory
        val viewModelFactory = KcalViewModelProviderFactory(requireActivity().application, kcalRepository)

        // Create an instance of KcalViewModel using the factory
        viewModel = ViewModelProvider(this, viewModelFactory).get(KcalViewModel::class.java)

        recyclerView = view.findViewById(R.id.rv_SearchFragment)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = AppAdapter(requireContext(), appList, viewModel)
        recyclerView.adapter = adapter

        loadData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_kcal, container, false)
    }

    private fun loadData() {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        compositeDisposable.add(
            apiService.getKcalData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    handleResponse(response)
                }, { error ->
                    handleError(error)
                })
        )
    }


    private fun handleResponse(response: List<Kcal>) {
        appList.clear()
        appList.addAll(response)
        adapter.notifyDataSetChanged()
        println("response: $response")

    }

    private fun handleError(error: Throwable) {
        println("İstek hatası: ${error.message}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }




}