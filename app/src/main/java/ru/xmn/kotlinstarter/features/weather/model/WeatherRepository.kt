package ru.xmn.kotlinstarter.features.weather.model

import io.reactivex.Flowable
import ru.xmn.kotlinstarter.services.weather.WeatherService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository
@Inject constructor(val service: WeatherService) {
    fun getWeatherData(): Flowable<WeatherData> {
        return service.getWeather()
    }
}