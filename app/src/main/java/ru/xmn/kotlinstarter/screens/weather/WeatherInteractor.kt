package ru.xmn.kotlinstarter.screens.weather

import io.reactivex.Flowable
import io.reactivex.Single
import ru.xmn.kotlinstarter.application.di.scopes.ActivityScope
import javax.inject.Inject

@ActivityScope
class WeatherInteractor
@Inject constructor(val weatherRepository: WeatherRepository) {
    fun getValue(): Flowable<WeatherData> {
        return weatherRepository.getValue()
    }

}