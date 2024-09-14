package com.example.newweather.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.newweather.model.WeatherData
import com.example.newweather.model.WeatherData2
import com.example.newweather.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherData = MutableStateFlow<WeatherData2?>(null)
    val weatherData: StateFlow<WeatherData2?> get() = _weatherData

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>get() = _errorMessage

    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            val response = repository.getWeather(city, apiKey)
            if (response != null) {
                _weatherData.value = response
            } else {
                _errorMessage.value = "Failed to fetch weather data"
            }
            _isLoading.value = false
        }
    }}

class WeatherViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}




