package ru.xmn.kotlinstarter.application.di

import dagger.Component
import ru.xmn.kotlinstarter.screens.AbstractComponent
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class
))
interface ApplicationComponent {
    fun abstractInteractorComponent(): AbstractComponent.Builder
}

