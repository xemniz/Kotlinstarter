package ru.xmn.kotlinstarter.features.mamsy.dataaccess

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.xmn.kotlinstarter.application.di.addParameterInterceptor
import ru.xmn.kotlinstarter.application.di.modify
import ru.xmn.kotlinstarter.application.di.provideRestAdapter
import javax.inject.Named
import javax.inject.Singleton

@Module
class WeatherNetworkModule {
    companion object {
        const val NAME = "weather"
    }

    @Provides
    @Singleton
    @Named(NAME)
    fun provideRestAdapterWeather(client: OkHttpClient, moshi: Moshi): Retrofit =
            provideRestAdapter(client.modify {
                hostnameVerifier { _, _ -> true }
                addParameterInterceptor("appid", "e0ef3bbfd145508364544cc6cc4876f7")
            },
                    "http://api.openweathermap.org/data/2.5/", moshi)

    @Provides
    @Singleton
    fun providesWeatherService(@Named(NAME) retrofit: Retrofit): WeatherNetworkApi
            = retrofit.create(WeatherNetworkApi::class.java)
}

// http://api.openweathermap.org/data/2.5/forecast?q=moscow&appid=e0ef3bbfd145508364544cc6cc4876f7&cnt=7