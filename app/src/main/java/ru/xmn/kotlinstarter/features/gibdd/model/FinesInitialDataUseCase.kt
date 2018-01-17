package ru.xmn.kotlinstarter.features.gibdd.model

class FinesInitialDataUseCase(private val finesDataDao: FinesDataDao) {
    var drivingLicense: String = ""
    var carDocs: String = ""
    var carReg: String = ""


    fun saveDrivingLicense(number: String) {
        drivingLicense = number
    }

    fun saveCarDocs(number: String) {
        carDocs = number
    }

    fun saveCarReg(number: String) {
        carReg = number
    }

    fun persistInitialData(){
        if (drivingLicense.isNotEmpty())
            finesDataDao.saveDrivingLicense(drivingLicense)
        if (carReg.isNotEmpty() && carDocs.isNotEmpty()){
            finesDataDao.saveCarReg(carReg)
            finesDataDao.saveCarDocs(carDocs)
        }
    }
}