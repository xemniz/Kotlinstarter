package ru.xmn.kotlinstarter.screens.weather

import io.reactivex.Flowable
import io.reactivex.Single
import ru.xmn.kotlinstarter.services.weather.WeatherService
import ru.xmn.kotlinstarter.services.weather.WeatherServiceModule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository
@Inject constructor(val service: WeatherService) {
    fun getValue(): Flowable<WeatherData> {
        return service.getWeather()
    }
}