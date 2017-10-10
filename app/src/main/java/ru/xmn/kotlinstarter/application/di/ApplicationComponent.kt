package ru.xmn.kotlinstarter.application.di

import dagger.Component
import ru.xmn.kotlinstarter.screens.AbstractComponent
import ru.xmn.kotlinstarter.screens.weather.WeatherComponent
import ru.xmn.kotlinstarter.screens.weather.WeatherModule
import ru.xmn.kotlinstarter.services.weather.WeatherServiceModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        NetworkModule::class,
        WeatherServiceModule::class
))
interface ApplicationComponent {
    fun abstractInteractorComponent(): AbstractComponent.Builder
    fun weatherInteractorComponent(): WeatherComponent.Builder
}

