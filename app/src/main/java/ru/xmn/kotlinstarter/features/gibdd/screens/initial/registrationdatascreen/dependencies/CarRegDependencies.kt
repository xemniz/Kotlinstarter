package ru.xmn.kotlinstarter.features.gibdd.screens.initial.registrationdatascreen.dependencies

import ru.xmn.kotlinstarter.features.gibdd.model.FinesInitialDataUseCase

class CarRegDependencies(finesDataDao: FinesInitialDataUseCase) : InitialScreenDependencies {
    override val descriptionText = "Укажите регистрационный номер ТС"
    override val hintText = "A777AA77"
    override val uniqueKey = "CarRegDependencies"
    override val textLength = 8
    override val verify: (String) -> Boolean = {
        it.matches("$RUSSIAN_ENGLISH_LETTERS_PATTERN\\d{3}$RUSSIAN_ENGLISH_LETTERS_PATTERN{2}\\d{2}".toRegex())
    }
    override val saveNumberStrategy: InitialScreenDependencies.SaveNumberStrategy = object: InitialScreenDependencies.SaveNumberStrategy {
        override fun save(text: String) {
             finesDataDao.saveCarReg(text)
        }

        override fun get(): String {
            return finesDataDao.carReg
        }
    }
}