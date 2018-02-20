package ru.xmn.kotlinstarter.application.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.xmn.kotlinstarter.application.App
import ru.xmn.kotlinstarter.features.mamsy.domain.WeatherComponent
import javax.inject.Singleton

@Module(subcomponents = arrayOf(WeatherComponent::class))
class ApplicationModule(private val app: App) {
    @Provides
    @Singleton
    fun provideApplicationContext(): Context = app
}