package ui.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ui.profile.ProfileScreenViewModel

actual val viewModelModule = module {
    singleOf(::ProfileScreenViewModel)
}