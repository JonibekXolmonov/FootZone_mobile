package uz.mobile.footzone.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import uz.mobile.footzone.api.ApiService
import uz.mobile.footzone.api.KtorApi
import uz.mobile.footzone.api.KtorApiImpl

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(
        apiModule,
        repositoryModule,
        useCaseModule,
        viewModelModule,
        platformModule,
        coreModule
    )
}

private val coreModule = module {

}

private val apiModule = module {
    single<KtorApi> { KtorApiImpl() }
    factory { ApiService(get()) }
}

private val viewModelModule = module {
}

private val repositoryModule = module {

}

private val useCaseModule = module {

}

fun initKoin() = initKoin {}
