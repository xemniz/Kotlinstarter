package ru.xmn.kotlinstarter.features.gibdd.screens.initial.registration_data_screen

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.xmn.kotlinstarter.features.gibdd.screens.initial.registration_data_screen.dependencies.InitialScreenDependencies

class RegistrationDataViewModel(
        val textLength: Int,
        val verify: (String) -> Boolean,
        val saveStrategy: InitialScreenDependencies.RegistrationNumberDao
) : ViewModel() {
    val verifiedState: MutableLiveData<VerifiedState> = MutableLiveData()

    init {
        verifiedState.value = VerifiedState.ShortText
    }

    fun onTextChange(text: String) {
        when {
            text.length < textLength -> verifiedState.value = VerifiedState.ShortText
            verify(text) -> verifiedState.value = VerifiedState.Verified
            !verify(text) -> verifiedState.value = VerifiedState.NotVerified
        }
    }

    fun save(text: String) {
        saveStrategy.save(text)
    }
}