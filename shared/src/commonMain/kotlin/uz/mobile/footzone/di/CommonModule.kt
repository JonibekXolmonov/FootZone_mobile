package uz.mobile.footzone.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.binds
import org.koin.dsl.module
import uz.mobile.footzone.data.api.network.api.KtorApi
import uz.mobile.footzone.data.api.network.api.impl.KtorApiImpl
import uz.mobile.footzone.data.api.network.service.AuthApiService
import uz.mobile.footzone.data.api.network.service.impl.AuthApiServiceImpl
import uz.mobile.footzone.data.settings.SettingsSource
import uz.mobile.footzone.data.settings.SettingsSourceImpl
import uz.mobile.footzone.remote.RemoteDataSource
import uz.mobile.footzone.remote.impl.RemoteDataSourceImpl
import uz.mobile.footzone.repository.AuthRepository
import uz.mobile.footzone.repository.impl.AuthRepositoryImpl
import uz.mobile.footzone.usecase.AuthUseCase
import uz.mobile.footzone.usecase.impl.AuthUseCaseImpl
import uz.mobile.footzone.viewmodel.login.AuthViewModel

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

    //ds
    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    single<SettingsSource> { SettingsSourceImpl(get()) }
}


private val networkModule = module {
    single<KtorApi> { KtorApiImpl(get()) }
}

private val useCaseModule = module {
    single<AuthUseCase> { AuthUseCaseImpl(get()) }
}

private val apiModule = module {
    single { AuthApiServiceImpl(get()) } binds arrayOf(AuthApiService::class, KtorApi::class)
}

private val viewModelModule = module {
    single { AuthViewModel() }
}


fun initKoin() = initKoin {}
