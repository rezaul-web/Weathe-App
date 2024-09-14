package com.example.newweather.model


import com.google.gson.annotations.SerializedName

data class WeatherData(
    val name: String, // City name
    val main: Main,
    val weather: List<WeatherDescription>,
)

data class Main(
    val temp: Float,   // Temperature
    val humidity: Int  // Humidity
)

data class WeatherDescription(
    @SerializedName("description")
    val description: String, // Weather description (e.g., "clear sky")
    @SerializedName("icon")
    val icon: String         // Weather icon code (e.g., "01d")
)
