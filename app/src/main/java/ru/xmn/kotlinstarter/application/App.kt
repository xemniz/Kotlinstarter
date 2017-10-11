package ru.xmn.kotlinstarter.application

import android.app.Application
import ru.xmn.kotlinstarter.application.di.ApplicationComponent
import ru.xmn.kotlinstarter.application.di.ApplicationModule
import ru.xmn.kotlinstarter.application.di.DaggerApplicationComponent


class App : Application() {

    companion object {
        lateinit var component: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    fun initializeDagger() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}
