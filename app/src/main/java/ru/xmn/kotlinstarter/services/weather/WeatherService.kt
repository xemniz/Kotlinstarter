package ru.xmn.kotlinstarter.services.weather

import io.reactivex.Flowable
import retrofit2.http.GET
import ru.xmn.kotlinstarter.features.weather.model.WeatherData

interface WeatherService {
    @GET("data/2.5/forecast/daily?id=524901&appid=b1b15e88fa797225412429c1c50c122a1")
    fun getWeather(): Flowable<WeatherData>
}