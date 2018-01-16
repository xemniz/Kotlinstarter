package ru.xmn.kotlinstarter.features.gibdd.screens.initial.registration_data_screen

sealed class VerifiedState {
    object ShortText : VerifiedState()
    object Verified : VerifiedState()
    object NotVerified : VerifiedState()
}