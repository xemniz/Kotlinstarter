package ru.xmn.kotlinstarter.features.gibdd.screens.initial.registrationdatascreen.dependencies

interface InitialScreenDependencies {
    val uniqueKey: String
    val textLength: Int
    val verify: (String) -> Boolean
    val saveNumberStrategy: SaveNumberStrategy
    val descriptionText: String
    val hintText: String


    interface SaveNumberStrategy {
        fun save(text: String)
        fun get(): String
    }
}

const val RUSSIAN_ENGLISH_LETTERS_PATTERN = "[АВЕКМНОРСТУХ]"
