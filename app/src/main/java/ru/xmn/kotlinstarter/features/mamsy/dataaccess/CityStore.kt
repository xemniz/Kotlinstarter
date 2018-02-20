package ru.xmn.kotlinstarter.features.mamsy.dataaccess

import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.kotlin.where
import ru.xmn.kotlinstarter.features.mamsy.domain.CityData
import javax.inject.Inject

class CityStore @Inject constructor() {
    init {
        Realm.getDefaultInstance().use { realm ->
            val initialCitiesNeed = realm.where<CityDataRealm>().count() == 0L
            if (initialCitiesNeed){
                realm.executeTransaction {
                    it.copyToRealmOrUpdate(CityDataRealm().apply { name = "Moscow" })
                    it.copyToRealmOrUpdate(CityDataRealm().apply { name = "London" })
                    it.copyToRealmOrUpdate(CityDataRealm().apply { name = "Berlin" })
                    it.copyToRealmOrUpdate(CityDataRealm().apply { name = "Tokio" })
                    it.copyToRealmOrUpdate(CityDataRealm().apply { name = "Manchester" })
                    it.copyToRealmOrUpdate(CityDataRealm().apply { name = "New Yorke" })
                }
            }
        }

    }

    fun getCities(): Flowable<List<CityData>> {
        Realm.getDefaultInstance().let { realm ->
            return realm.where<CityDataRealm>()
                    .findAllAsync()
                    .asFlowable()
                    .filter { it.isLoaded }
                    .map { it.map { CityData(it.name) } }
        }
    }

    fun addCity(cityData: CityData) {
        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                realm.copyToRealmOrUpdate(CityDataRealm.create(cityData))
            }
        }
    }

}

open class CityDataRealm : RealmObject() {
    @PrimaryKey lateinit var name: String

    companion object {
        fun create(cityData: CityData) = CityDataRealm().apply { name = cityData.name }
    }
}