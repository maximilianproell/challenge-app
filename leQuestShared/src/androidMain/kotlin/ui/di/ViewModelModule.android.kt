package ui.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ui.home.HomeScreenViewModel
import ui.profile.ProfileScreenViewModel

actual val viewModelModule = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::ProfileScreenViewModel)
}