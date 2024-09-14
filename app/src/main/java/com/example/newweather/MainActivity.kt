package com.example.newweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.newweather.repository.WeatherRepository
import com.example.newweather.retrofit.RetrofitClient
import com.example.newweather.viewmodel.WeatherViewModel
import com.example.newweather.viewmodel.WeatherViewModelFactory


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weatherApi = RetrofitClient.instance
        val repository = WeatherRepository(weatherApi)

        setContent {

            val weatherViewModel: WeatherViewModel = ViewModelProvider(
                this,
                WeatherViewModelFactory(repository)
            )[WeatherViewModel::class.java]

            WeatherScreen(viewModel = weatherViewModel)
        }
    }
}


