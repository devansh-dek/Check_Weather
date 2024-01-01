package com.example.checkweather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {


    @GET("weather")
     fun getweatherDeatils(
        @Query("q")city : String,
        @Query("appid")appid : String,
        @Query("units")units : String
    ): Call<weatherDEtails>


}