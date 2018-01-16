package ru.xmn.kotlinstarter.features.gibdd.screens.initial

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import ru.xmn.kotlinstarter.features.gibdd.model.FinesDataDao
import ru.xmn.kotlinstarter.features.gibdd.screens.initial.registration_data_screen.dependencies.CarDocsDependencies
import ru.xmn.kotlinstarter.features.gibdd.screens.initial.registration_data_screen.dependencies.CarRegDependencies
import ru.xmn.kotlinstarter.features.gibdd.screens.initial.registration_data_screen.dependencies.DrivingLicenseDependencies
import ru.xmn.kotlinstarter.features.gibdd.screens.main.GibddMainViewModel


@Subcomponent(modules = arrayOf(GibddInitialModule::class))
interface GibddComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): GibddComponent
    }

    fun inject(gibddMainViewModel: GibddMainViewModel)

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
    fun drivingLicenseDependencies(finesDataDao: FinesDataDao): DrivingLicenseDependencies {
        return DrivingLicenseDependencies(finesDataDao)
    }

    @Provides
    fun carDocsDependencies(finesDataDao: FinesDataDao): CarDocsDependencies {
        return CarDocsDependencies(finesDataDao)
    }

    @Provides
    fun carRegDependencies(finesDataDao: FinesDataDao): CarRegDependencies {
        return CarRegDependencies(finesDataDao)
    }
}


