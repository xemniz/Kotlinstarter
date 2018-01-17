package ru.xmn.kotlinstarter.features.gibdd.screens.initial.registrationdatascreen.dependencies

abstract class DocumentNumberDependencies: InitialScreenDependencies {
    override val textLength = 10
    override val verify: (String) -> Boolean = {
        it.matches("\\d{2}$RUSSIAN_ENGLISH_LETTERS_PATTERN{2}\\d{6}".toRegex())
    }
}