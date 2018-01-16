package ru.xmn.kotlinstarter.features.gibdd.screens.initial.registration_data_screen.dependencies

import ru.xmn.kotlinstarter.features.gibdd.model.FinesDataDao

class CarDocsDependencies(finesDataDao: FinesDataDao) : DocumentNumberDependencies() {
    override val descriptionText = "Введите номер свидетельства о регистрации ТС"
    override val hintText = "77OO777777"
    override val registrationNumberDao: InitialScreenDependencies.RegistrationNumberDao = object: InitialScreenDependencies.RegistrationNumberDao {
        override fun save(text: String) {
             finesDataDao.saveCarDocs(text)
        }

        override fun get(): String {
            return finesDataDao.carDocs
        }
    }
}