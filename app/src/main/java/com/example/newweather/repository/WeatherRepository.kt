package com.example.newweather.repository


import com.example.newweather.model.WeatherData2
import com.example.newweather.retrofit.WeatherApi
import retrofit2.Response

class WeatherRepository(private val api: WeatherApi) {

    suspend fun getWeather(city: String, apiKey: String): WeatherData2? {
        return try {
            val response: Response<WeatherData2> = api.getWeather(city, apiKey)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}