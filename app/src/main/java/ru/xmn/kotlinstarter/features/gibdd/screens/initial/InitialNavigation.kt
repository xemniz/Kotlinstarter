package ru.xmn.kotlinstarter.features.gibdd.screens.initial

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class InitialNavigation : ViewModel() {
    val screenState: MutableLiveData<ScreenState> = MutableLiveData()

    private var currentScreenIndex = 0
    private val screens: Array<ScreenState> = arrayOf(
            ScreenState.Start,
            ScreenState.CarReg,
            ScreenState.CarDocs,
            ScreenState.DrivingLicense,
            ScreenState.MainScreen
    )

    init {
        sendCurrentScreen()
    }

    fun goNext() {
        currentScreenIndex++
        sendCurrentScreen()
    }

    fun goBack(): Boolean {
        if (currentScreenIndex == 0)
            return false
        else {
            currentScreenIndex--
            sendCurrentScreen()
            return true
        }
    }

    private fun sendCurrentScreen() {
        if (currentScreenIndex < 0) {
            currentScreenIndex = 0
        }

        if (currentScreenIndex > screens.size - 1) {
            currentScreenIndex = screens.size - 1
        }

        screenState.value = screens[currentScreenIndex]
    }
}

sealed class ScreenState {
    object Start : ScreenState()
    object CarReg : ScreenState(){
        fun key(): String = ""
    }
    object CarDocs : ScreenState()
    object DrivingLicense : ScreenState()
    object MainScreen : ScreenState()
}