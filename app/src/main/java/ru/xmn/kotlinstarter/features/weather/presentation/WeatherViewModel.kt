package ru.xmn.kotlinstarter.features.weather.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.xmn.kotlinstarter.application.App
import ru.xmn.kotlinstarter.features.weather.model.WeatherUseCase
import javax.inject.Inject

class WeatherViewModel : ViewModel() {

    @Inject
    lateinit var weatherUseCase: WeatherUseCase

    val weatherLiveData: MutableLiveData<WeatherState> = MutableLiveData()

    init {
        App.component.weatherInteractorComponent().build().inject(this)
        weatherUseCase.getValue()
                .map { WeatherState.Success(it) as WeatherState }
                .startWith(WeatherState.Loading)
                .onErrorReturn { WeatherState.Error(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{weatherLiveData.value = it}
    }
}