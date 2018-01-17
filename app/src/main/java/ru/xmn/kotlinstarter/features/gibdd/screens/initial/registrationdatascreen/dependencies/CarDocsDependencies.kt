package ru.xmn.kotlinstarter.features.gibdd.screens.initial.registrationdatascreen.dependencies

import ru.xmn.kotlinstarter.features.gibdd.model.FinesInitialDataUseCase

class CarDocsDependencies(finesDataDao: FinesInitialDataUseCase) : DocumentNumberDependencies() {
    override val uniqueKey = "CarDocsDependencies"
    override val descriptionText = "Введите номер свидетельства о регистрации ТС"
    override val hintText = "77OO777777"
    override val saveNumberStrategy: InitialScreenDependencies.SaveNumberStrategy = object: InitialScreenDependencies.SaveNumberStrategy {
        override fun save(text: String) {
             finesDataDao.saveCarDocs(text)
        }

        override fun get(): String {
            return finesDataDao.carDocs
        }
    }
}