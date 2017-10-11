package ru.xmn.kotlinstarter.features.weather.model

import io.reactivex.Flowable
import javax.inject.Inject

class WeatherUseCase
@Inject constructor(val weatherRepository: WeatherRepository) {
    fun getValue(): Flowable<WeatherData> {
        return weatherRepository.getWeatherData()
    }

}