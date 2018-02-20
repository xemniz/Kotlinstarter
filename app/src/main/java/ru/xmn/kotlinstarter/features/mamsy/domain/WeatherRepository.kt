package ru.xmn.kotlinstarter.features.mamsy.domain

import io.reactivex.Single
import ru.xmn.kotlinstarter.features.mamsy.dataaccess.WeatherService
import javax.inject.Inject

class WeatherRepository
@Inject constructor(val weatherService: WeatherService) {
    fun weekWeather(cityData: CityData): Single<WeekWeatherData> {
        return weatherService.weekWeather(cityData.name)
    }
}

data class WeekWeatherData(val days: List<WeatherDayData>)

data class WeatherDayData (val day: String, val weather: String, val temperatureDay: String, val temperatureNight: String)

