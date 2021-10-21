package czajkowski.patryk.testsheetapp.home.repository.db

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val daoModule = module {

    single<DummyDataDao> { DummyDataDao_Impl(DummyDatabase.getInstance(androidApplication())) }

}