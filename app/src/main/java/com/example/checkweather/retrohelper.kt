package com.example.checkweather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object retrohelper {

    val BASE = "https://api.openweathermap.org/data/2.5/"


    fun getInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }





}