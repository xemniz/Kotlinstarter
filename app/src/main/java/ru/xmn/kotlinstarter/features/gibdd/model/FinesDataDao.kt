package ru.xmn.kotlinstarter.features.gibdd.model

import android.content.Context
import ru.xmn.common.extensions.bindSharedPreference

class FinesDataDao(context: Context) {
    var drivingLicense by bindSharedPreference(context, "drivingLicense", "")
    var carDocs by bindSharedPreference(context, "carDocs", "")
    var carReg by bindSharedPreference(context, "carReg", "")

    fun saveDrivingLicense(number: String) {
        drivingLicense = number
    }

    fun saveCarDocs(number: String) {
        carDocs = number
    }

    fun saveCarReg(number: String) {
        carReg = number
    }

}