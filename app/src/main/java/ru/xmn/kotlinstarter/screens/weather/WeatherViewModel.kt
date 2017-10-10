package ru.xmn.kotlinstarter.screens.weather

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.xmn.kotlinstarter.application.App
import javax.inject.Inject

class WeatherViewModel : ViewModel() {

    @Inject
    lateinit var weatherInteractor: WeatherInteractor

    val weatherLiveData: MutableLiveData<WeatherState> = MutableLiveData()

    init {
        App.component.weatherInteractorComponent().provideModule(WeatherModule()).build().inject(this)
        weatherInteractor.getValue()
                .doOnNext { println("spisok $it)") }
                .map { WeatherState.Success(it) as WeatherState }
                .startWith(WeatherState.Loading)
                .onErrorReturn { WeatherState.Error(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{weatherLiveData.value = it}
    }
}