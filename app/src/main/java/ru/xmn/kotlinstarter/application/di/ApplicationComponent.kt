package ru.xmn.kotlinstarter.application.di

import dagger.Component
import ru.xmn.kotlinstarter.features.mamsy.dataaccess.WeatherNetworkModule
import ru.xmn.kotlinstarter.features.mamsy.domain.WeatherComponent
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        NetworkModule::class,
        WeatherNetworkModule::class
))
interface ApplicationComponent {
    fun weatherComponent(): WeatherComponent.Builder
}

