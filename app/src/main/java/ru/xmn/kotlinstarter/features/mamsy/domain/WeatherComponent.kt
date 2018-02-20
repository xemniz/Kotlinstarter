package ru.xmn.kotlinstarter.features.mamsy.domain

import dagger.Subcomponent
import ru.xmn.kotlinstarter.features.mamsy.presentation.CitiesViewModel
import ru.xmn.kotlinstarter.features.mamsy.presentation.CityWeatherViewModel

@Subcomponent()
interface WeatherComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): WeatherComponent
    }

    fun citiesViewModel(): CitiesViewModel

    fun cityWeatherViewModel(): CityWeatherViewModel
}