package ru.xmn.kotlinstarter.features.mamsy.presentation

import ru.xmn.kotlinstarter.features.mamsy.domain.WeekWeatherData

sealed class WeakWeatherDataState {
    object Loading : WeakWeatherDataState()
    class Success(val data: WeekWeatherData) : WeakWeatherDataState()
    class Error(val error: Throwable) : WeakWeatherDataState()
}