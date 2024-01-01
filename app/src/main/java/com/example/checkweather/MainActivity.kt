package com.example.checkweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import com.example.checkweather.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//69578036896b6fecffb5f086436f472a

class MainActivity : AppCompatActivity() {


    lateinit var binding :ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        SearchCity()

        SetData("goa")
Log.d("HELLLLLLLLLLLLLLLLL","IS HEREEEEEEEEEEEEEEEEEEEEEEEe")
        Toast.makeText(this,"Ran the application ",Toast.LENGTH_SHORT).show()

    }

    private fun SearchCity() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {

                if (p0 != null) {
                    SetData(p0)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

return true



            }


        })
    }


    private fun SetData(city:String){
          Log.d("HELLLLLLLLLLLLLLLL", "RAN INTO THTE FUNC")

    //    val retro = retrohelper.getInstance().create(WeatherApi::class.java)
      val retro = Retrofit.Builder()
          .addConverterFactory(GsonConverterFactory.create())
          .baseUrl("https://api.openweathermap.org/data/2.5/")
          .build()
          .create(WeatherApi::class.java)
        val response = retro.getweatherDeatils(city,"69578036896b6fecffb5f086436f472a","metric")
if(response==null){
    Toast.makeText(applicationContext,"bhai null h",Toast.LENGTH_SHORT).show()
}
          Log.d("API_REQUEST", response.request().url().toString())

        response.enqueue(object : Callback<weatherDEtails> {
            override fun onResponse(call: Call<weatherDEtails>, response: Response<weatherDEtails>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody !=null){
                    val temp = responseBody.main.temp.toString()
                    val humid = responseBody.main.humidity
                    val windSpeed = responseBody.wind.speed
                    val sunRise = responseBody.sys.sunrise
                    val sunSet = responseBody.sys.sunset
                    val seaLevel = responseBody.main.pressure
                    val condition = responseBody.weather.firstOrNull()?.main?:"unkown"
                    val MaxTemp = responseBody.main.temp_max
                    val MinTemp = responseBody.main.temp_min

                    binding.tempo.text = "$temp °C"
                    binding.humid.text = "$humid %"
                    binding.minTemp.text = "MIN TEMP : $MinTemp"
                    binding.maxTemp.text = "MAX TEMP : $MaxTemp"
                    binding.windo.text = "$windSpeed m/s"
                    binding.suno.text = "$sunRise"
                    binding.sunro.text = "$sunSet"
                    binding.condo.text= condition
                    binding.conditionS.text= condition
                    binding.sealvl.text = "$seaLevel"
                binding.weekDay.text = getDayOfWeek(Date())
            binding.weekDate.text = getCurrentDateAsString()
                    binding.cityName.text = "$city"
                    Log.d("TTT","Raninto here!!!!!!!!!!!!")


changeWeatherCondition(condition)



                }
                else{
                    Log.d("YOOOOOOOOOOOO","YOYOYOYOO MANNNNNNNNNN")
                    Toast.makeText(applicationContext, "$city not available", Toast.LENGTH_SHORT).show()


                }

            }

            override fun onFailure(call: Call<weatherDEtails>, t: Throwable) {

                Toast.makeText(applicationContext,"APPLICATION NOT WORKING! CHECK INTERNET CONNECTION!",Toast.LENGTH_LONG).show()

            }


        })


    }

    private fun changeWeatherCondition(condition: String) {
        when(condition){

            "Clear Sky","Sunny","Clear"->{
                binding.root.setBackgroundResource(R.drawable.sunnyy)
                binding.lottieAnimationView.setAnimation(R.raw.sun)

            }
            "Partly Clouds" ,"Clouds","Overcast","Mist","Foggy"->{
                binding.root.setBackgroundResource(R.drawable.cloudy)
                binding.lottieAnimationView.setAnimation(R.raw.cloud)

            }
             "Light Rain","Drizzle","Moderate Rain","Showers","Heavy Rain"->{
                binding.root.setBackgroundResource(R.drawable.rainy)
                binding.lottieAnimationView.setAnimation(R.raw.rain)

            }
             "Light Snow","Moderate Snow","Heavy Snow","Blizzard"->{
                binding.root.setBackgroundResource(R.drawable.snowy)
                binding.lottieAnimationView.setAnimation(R.raw.snow)

            }
            else ->{

                binding.root.setBackgroundResource(R.drawable.sunnyy)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }




        }
binding.lottieAnimationView.playAnimation()
    }

    fun getDayOfWeek(date: Date): String {
        val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        return dateFormat.format(date)
    }
    fun getCurrentDateAsString(): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

}