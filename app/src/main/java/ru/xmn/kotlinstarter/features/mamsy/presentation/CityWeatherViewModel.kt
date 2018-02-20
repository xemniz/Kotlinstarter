package ru.xmn.kotlinstarter.features.mamsy.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.xmn.kotlinstarter.features.mamsy.domain.CityData
import ru.xmn.kotlinstarter.features.mamsy.domain.WeatherRepository
import javax.inject.Inject

class CityWeatherViewModel
@Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {
    val cityWeather: MutableLiveData<WeakWeatherDataState> = MutableLiveData()

    private var disposable: Disposable? = null
    private var flowable: Flowable<WeakWeatherDataState>? = null

    fun clickRetry() {
        fireCurrentFlowable()
    }

    fun loadWeekWeather(cityData: CityData) {
        flowable = weatherRepository
                .weekWeather(cityData)
                .toFlowable()
                .map { WeakWeatherDataState.Success(it) as WeakWeatherDataState }
                .startWith(WeakWeatherDataState.Loading)
                .onErrorReturn { WeakWeatherDataState.Error(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

        fireCurrentFlowable()
    }

    private fun fireCurrentFlowable() {
        disposable = flowable?.subscribe { cityWeather.value = it }
    }

    override fun onCleared() {
        disposable?.dispose()
    }
}