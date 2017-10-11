package ru.xmn.kotlinstarter.application.di

import dagger.Component
import ru.xmn.kotlinstarter.features.weather.model.WeatherComponent
import ru.xmn.kotlinstarter.services.weather.WeatherServiceModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        NetworkModule::class,
        WeatherServiceModule::class
))
interface ApplicationComponent {
    fun weatherInteractorComponent(): WeatherComponent.Builder
}

