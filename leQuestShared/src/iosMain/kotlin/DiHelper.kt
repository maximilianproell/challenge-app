import data.di.dataModule
import org.koin.core.context.startKoin
import ui.di.viewModelModule

fun initKoin() {
    startKoin {
        modules(dataModule, viewModelModule)
    }
}