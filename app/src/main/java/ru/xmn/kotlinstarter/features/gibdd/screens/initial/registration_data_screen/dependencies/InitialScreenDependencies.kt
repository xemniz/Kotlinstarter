package ru.xmn.kotlinstarter.features.gibdd.screens.initial.registration_data_screen.dependencies

interface InitialScreenDependencies {
    val uniqueKey: String
    val textLength: Int
    val verify: (String) -> Boolean
    val registrationNumberDao: RegistrationNumberDao
    val descriptionText: String
    val hintText: String


    interface RegistrationNumberDao {
        fun save(text: String)
        fun get(): String
    }
}

const val RUSSIAN_ENGLISH_LETTERS_PATTERN = "[АВЕКМНОРСТУХ]"
