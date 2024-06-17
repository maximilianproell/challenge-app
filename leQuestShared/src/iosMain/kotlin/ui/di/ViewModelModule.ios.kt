package ui.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ui.profile.ProfileScreenViewModel
import ui.home.HomeScreenViewModel

actual val viewModelModule = module {
    singleOf(::HomeScreenViewModel)
    singleOf(::ProfileScreenViewModel)
}