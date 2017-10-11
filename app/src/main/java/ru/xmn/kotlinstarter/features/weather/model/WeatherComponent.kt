package ru.xmn.kotlinstarter.features.weather.model

import dagger.Subcomponent
import ru.xmn.kotlinstarter.features.weather.presentation.WeatherViewModel

@Subcomponent()
interface WeatherComponent {
    fun inject(weatherViewModel: WeatherViewModel)

    @Subcomponent.Builder
    interface Builder {
        fun build(): WeatherComponent
    }
}