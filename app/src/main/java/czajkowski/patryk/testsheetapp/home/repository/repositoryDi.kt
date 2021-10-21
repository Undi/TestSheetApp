package czajkowski.patryk.testsheetapp.home.repository

import org.koin.dsl.module

val repositoryModule = module {
    single { DummyDataRepository(get(), get()) }
}