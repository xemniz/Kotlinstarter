package ru.xmn.kotlinstarter.features.gibdd.screens.initial.registration_data_screen.dependencies

import ru.xmn.kotlinstarter.features.gibdd.model.FinesDataDao

class DrivingLicenseDependencies(finesDataDao: FinesDataDao) : DocumentNumberDependencies() {
    override val descriptionText = "Введите номер водительского удостоверения"
    override val hintText = "77OO777777"

    override val registrationNumberDao: InitialScreenDependencies.RegistrationNumberDao = object: InitialScreenDependencies.RegistrationNumberDao {
        override fun save(text: String) {
             finesDataDao.saveDrivingLicense(text)
        }

        override fun get(): String {
            return finesDataDao.drivingLicense
        }
    }
}

