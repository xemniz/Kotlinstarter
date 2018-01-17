package ru.xmn.kotlinstarter.features.gibdd.screens.initial

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import ru.xmn.kotlinstarter.application.di.scopes.ActivityScope
import ru.xmn.kotlinstarter.features.gibdd.model.FinesDataDao
import ru.xmn.kotlinstarter.features.gibdd.model.FinesInitialDataUseCase
import ru.xmn.kotlinstarter.features.gibdd.screens.initial.registrationdatascreen.dependencies.CarDocsDependencies
import ru.xmn.kotlinstarter.features.gibdd.screens.initial.registrationdatascreen.dependencies.CarRegDependencies
import ru.xmn.kotlinstarter.features.gibdd.screens.initial.registrationdatascreen.dependencies.DrivingLicenseDependencies
import ru.xmn.kotlinstarter.features.gibdd.screens.main.GibddMainViewModel

@ActivityScope
@Subcomponent(modules = arrayOf(GibddInitialModule::class))
interface GibddComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): GibddComponent
    }

    fun inject(gibddMainViewModel: GibddMainViewModel)

    fun finesInitialDataUseCase(): FinesInitialDataUseCase

    fun drivingLicenseDependencies(): DrivingLicenseDependencies
    fun carDocsDependencies(): CarDocsDependencies
    fun carRegDependencies(): CarRegDependencies
}

@Module
class GibddInitialModule {
    @Provides
    fun finesDataDao(context: Context): FinesDataDao {
        return FinesDataDao(context)
    }

    @Provides
    @ActivityScope
    fun finesInitialDataUseCase(finesDataDao: FinesDataDao): FinesInitialDataUseCase {
        return FinesInitialDataUseCase(finesDataDao)
    }

    @Provides
    fun drivingLicenseDependencies(finesInitialDataUseCase: FinesInitialDataUseCase): DrivingLicenseDependencies {
        return DrivingLicenseDependencies(finesInitialDataUseCase)
    }

    @Provides
    fun carDocsDependencies(finesInitialDataUseCase: FinesInitialDataUseCase): CarDocsDependencies {
        return CarDocsDependencies(finesInitialDataUseCase)
    }

    @Provides
    fun carRegDependencies(finesInitialDataUseCase: FinesInitialDataUseCase): CarRegDependencies {
        return CarRegDependencies(finesInitialDataUseCase)
    }
}


