package ru.xmn.kotlinstarter.screens.weather

sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(val value: WeatherData) : WeatherState()
    data class Error(val error: Throwable) : WeatherState(){
        init {
            println(error)
        }
    }
}