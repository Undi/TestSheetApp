package czajkowski.patryk.testsheetapp

import android.app.Application
import czajkowski.patryk.testsheetapp.home.repository.api.serviceModule
import czajkowski.patryk.testsheetapp.home.repository.db.daoModule
import czajkowski.patryk.testsheetapp.home.repository.repositoryModule
import czajkowski.patryk.testsheetapp.home.viewmodel.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestSheetApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestSheetApp)
            modules(
                listOf(
                    serviceModule,
                    viewModelModule,
                    repositoryModule,
                    daoModule
                )
            )
        }
    }
}
