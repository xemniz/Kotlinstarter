package ru.xmn.kotlinstarter.features.mamsy.domain

import io.reactivex.Flowable
import ru.xmn.kotlinstarter.features.mamsy.dataaccess.CityStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CitiesRepository
@Inject constructor(private val cityStore: CityStore) {
    fun towns(): Flowable<List<CityData>> {
        return cityStore.getCities()
    }

    fun addCity(cityData: CityData) {
        cityStore.addCity(cityData)
    }
}

data class CityData(val name: String)
