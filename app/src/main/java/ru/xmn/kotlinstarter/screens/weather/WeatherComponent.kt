package ru.xmn.kotlinstarter.screens.weather

import dagger.Subcomponent
import ru.xmn.kotlinstarter.application.di.scopes.ActivityScope

@ActivityScope
@Subcomponent(modules = arrayOf(WeatherModule::class))
interface WeatherComponent {
    fun inject(weatherViewModel: WeatherViewModel)

    @Subcomponent.Builder
    interface Builder {
        fun build(): WeatherComponent
        fun provideModule(r: WeatherModule): Builder

    }
}