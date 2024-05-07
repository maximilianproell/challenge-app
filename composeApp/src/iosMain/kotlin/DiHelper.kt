import data.di.dataModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(dataModule)
    }
}