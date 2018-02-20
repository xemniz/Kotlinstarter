package ru.xmn.kotlinstarter.features.mamsy.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.xmn.kotlinstarter.features.mamsy.domain.CitiesRepository
import ru.xmn.kotlinstarter.features.mamsy.domain.CityData
import javax.inject.Inject

class CitiesViewModel
@Inject constructor(private val citiesRepository: CitiesRepository) : ViewModel() {
    val cities: MutableLiveData<List<CityData>> = MutableLiveData()

    init {
        citiesRepository.towns()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { cities.value = it }
    }

    fun addCity(cityData: CityData) {
        citiesRepository.addCity(cityData)
    }
}