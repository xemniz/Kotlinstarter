package ru.xmn.kotlinstarter.services.weather

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.xmn.kotlinstarter.application.di.provideRestAdapter
import javax.inject.Named
import javax.inject.Singleton

@Module
class WeatherServiceModule {
    companion object {
        const val NAME = "weather"
    }

    @Provides
    @Singleton
    @Named(NAME)
    fun provideRestAdapterWeather(client: OkHttpClient, moshi: Moshi): Retrofit
            = provideRestAdapter(client, "https://samples.openweathermap.org/", moshi)

    @Provides
    @Singleton
    fun providesWeatherService(@Named(NAME) retrofit: Retrofit): WeatherService
            = retrofit.create(WeatherService::class.java)
}
