package ru.xmn.kotlinstarter.features.mamsy.dataaccess

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherNetworkApi {
    @GET("forecast")
    fun getWeather(@Query("q") cityName: String, @Query("cnt") daysCount: Int): Single<WeatherData>
}