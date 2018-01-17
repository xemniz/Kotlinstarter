package ru.xmn.kotlinstarter.features.gibdd.screens.initial.registrationdatascreen

sealed class VerifiedState {
    object ShortText : VerifiedState()
    object Verified : VerifiedState()
    object NotVerified : VerifiedState()
}