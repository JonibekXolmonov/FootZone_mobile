package uz.mobile.footzone.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.binds
import org.koin.dsl.module
import uz.mobile.footzone.data.api.network.api.KtorApi
import uz.mobile.footzone.data.api.network.api.impl.KtorApiImpl
import uz.mobile.footzone.data.api.network.service.AuthApiService
import uz.mobile.footzone.data.api.network.service.StadiumApiService
import uz.mobile.footzone.data.api.network.service.impl.AuthApiServiceImpl
import uz.mobile.footzone.data.api.network.service.impl.StadiumApiServiceImpl
import uz.mobile.footzone.data.settings.SettingsSource
import uz.mobile.footzone.data.settings.SettingsSourceImpl
import uz.mobile.footzone.remote.RemoteDataSource
import uz.mobile.footzone.remote.impl.RemoteDataSourceImpl
import uz.mobile.footzone.repository.AuthRepository
import uz.mobile.footzone.repository.StadiumRepository
import uz.mobile.footzone.repository.impl.AuthRepositoryImpl
import uz.mobile.footzone.repository.impl.StadiumRepositoryImpl
import uz.mobile.footzone.usecase.AuthUseCase
import uz.mobile.footzone.usecase.StadiumUseCase
import uz.mobile.footzone.usecase.impl.AuthUseCaseImpl
import uz.mobile.footzone.usecase.impl.StadiumUseCaseImpl
import uz.mobile.footzone.viewmodel.login.AuthViewModel
import uz.mobile.footzone.viewmodel.main.MainViewModel

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(
        apiModule,
        networkModule,
        useCaseModule,
        viewModelModule,
        platformModule,
        coreModule
    )
}

private val coreModule = module {

    //repository
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<StadiumRepository> { StadiumRepositoryImpl(get()) }

    //ds
    single<RemoteDataSource> { RemoteDataSourceImpl(get(), get()) }
    single<SettingsSource> { SettingsSourceImpl(get()) }
}


private val networkModule = module {
    single<KtorApi> { KtorApiImpl(get()) }
}

private val useCaseModule = module {
    single<AuthUseCase> { AuthUseCaseImpl(get()) }
    single<StadiumUseCase> { StadiumUseCaseImpl(get()) }

}

private val apiModule = module {
    single { AuthApiServiceImpl(get()) } binds arrayOf(AuthApiService::class, KtorApi::class)
    single { StadiumApiServiceImpl(get()) } binds arrayOf(StadiumApiService::class, KtorApi::class)
}

private val viewModelModule = module {
    single { AuthViewModel() }
    single { MainViewModel() }
}


fun initKoin() = initKoin {}
