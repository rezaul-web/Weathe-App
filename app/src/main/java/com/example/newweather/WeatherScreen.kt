package com.example.newweather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.Coil
import coil.compose.AsyncImage
import com.example.newweather.repository.WeatherRepository
import com.example.newweather.retrofit.RetrofitClient
import com.example.newweather.viewmodel.WeatherViewModel
import kotlin.math.roundToInt
import kotlin.text.format

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    var city by remember { mutableStateOf("") }

    val weatherData by viewModel.weatherData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Enter City Name") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = {
                viewModel.fetchWeather(
                    city,
                    "6ff9d0bf62910d8123eecdfe3d9c3206"
                )
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> CircularProgressIndicator()
            errorMessage != null -> Text(text = errorMessage!!)
            weatherData != null -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = "https://openweathermap.org/img/wn/${weatherData!!.weather[0].icon}@2x.png",
                        contentDescription = null,
                        modifier = Modifier.matchParentSize()
                    )
                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.height(56.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "City",
                                tint = Color.Red
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = " ${weatherData!!.name}", fontSize = 32.sp)

                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = weatherData!!.main.temp.roundToInt().toString() + " °C",
                            fontSize = 90.sp
                        )
                        Spacer(modifier = Modifier.height(240.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            CardLayout {
                                Text(
                                    text = " ${weatherData!!.weather[0].description}",
                                    fontSize = 20.sp
                                )
                            }
                            CardLayout {
                                Text(
                                    text = "Humidity: ${weatherData!!.main.humidity}",
                                    fontSize = 20.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            CardLayout {
                                Text(
                                    text = " ${weatherData!!.wind.speed.roundToInt().toString()} Km/H",
                                    fontSize = 20.sp
                                )
                            }


                            CardLayout {
                                Text(
                                    text = "Pressure: ${weatherData!!.main.pressure} Pa",
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun CardLayout(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
        modifier = modifier
            .width(200.dp)
            .height(100.dp)
            .padding(10.dp), // Remove background, Card already provides it
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp), // Optional: Adds rounded corners to the card
        elevation = CardDefaults.cardElevation(4.dp) // Optional: Elevation for shadow
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), // Ensures the content fills the Card
            contentAlignment = Alignment.Center // Centers the content inside the Box
        ) {
            content() // The content is passed as a composable lambda
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CardPreview() {
    val weatherApi = RetrofitClient.instance
    val repository = WeatherRepository(weatherApi)
    WeatherScreen(viewModel = WeatherViewModel(repository))
}

