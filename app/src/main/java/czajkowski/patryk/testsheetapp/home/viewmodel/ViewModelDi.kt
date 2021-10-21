package czajkowski.patryk.testsheetapp.home.viewmodel

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}