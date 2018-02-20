package ru.xmn.kotlinstarter.features.mamsy.dataaccess

import io.reactivex.Single
import ru.xmn.common.extensions.dayName
import ru.xmn.kotlinstarter.features.mamsy.domain.WeatherDayData
import ru.xmn.kotlinstarter.features.mamsy.domain.WeekWeatherData
import java.util.*
import javax.inject.Inject

class WeatherService
@Inject constructor(val weatherNetworkApi: WeatherNetworkApi) {
    fun weekWeather(city: String): Single<WeekWeatherData> {
        return weatherNetworkApi.getWeather(city, 7).map {
            WeekWeatherData(it.list.map {
                WeatherDayData(Date(it.dt.toLong()).dayName(), it.weather[0].main, it.main.temp_min.toString(), it.main.temp_max.toString())
            })
        }
    }
}