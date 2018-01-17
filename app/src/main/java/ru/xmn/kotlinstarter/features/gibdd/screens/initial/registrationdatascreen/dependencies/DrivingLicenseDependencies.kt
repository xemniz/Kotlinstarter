package ru.xmn.kotlinstarter.features.gibdd.screens.initial.registrationdatascreen.dependencies

import ru.xmn.kotlinstarter.features.gibdd.model.FinesInitialDataUseCase

class DrivingLicenseDependencies(finesDataDao: FinesInitialDataUseCase) : DocumentNumberDependencies() {
    override val uniqueKey = "DrivingLicenseDependencies"
    override val descriptionText = "Введите номер водительского удостоверения"
    override val hintText = "77OO777777"

    override val saveNumberStrategy: InitialScreenDependencies.SaveNumberStrategy = object: InitialScreenDependencies.SaveNumberStrategy {
        override fun save(text: String) {
             finesDataDao.saveDrivingLicense(text)
        }

        override fun get(): String {
            return finesDataDao.drivingLicense
        }
    }
}

