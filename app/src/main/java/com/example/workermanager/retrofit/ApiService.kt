package com.example.workermanager.retrofit

import com.example.workermanager.classess.MoneyItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("arkhiv-kursov-valyut/json/")
    fun getUsers(): Call<List<MoneyItem>>
}