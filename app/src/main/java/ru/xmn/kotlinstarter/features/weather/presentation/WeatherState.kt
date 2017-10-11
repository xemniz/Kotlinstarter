package ru.xmn.kotlinstarter.features.weather.presentation

import ru.xmn.kotlinstarter.features.weather.model.WeatherData

sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(val value: WeatherData) : WeatherState()
    data class Error(val error: Throwable) : WeatherState(){
        init {
            println(error)
        }
    }
}