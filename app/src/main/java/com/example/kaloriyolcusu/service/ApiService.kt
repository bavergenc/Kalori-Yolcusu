package com.example.healtapp.service

import com.example.healtapp.model.Kcal
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("bavergenc/Kcal_Calculator/master/kcal_json.txt")
    fun getKcalData(): Observable<List<Kcal>>
}