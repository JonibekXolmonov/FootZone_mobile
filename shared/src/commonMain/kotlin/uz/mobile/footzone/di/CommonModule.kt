package uz.mobile.footzone.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.binds
import org.koin.dsl.module
import uz.mobile.footzone.data.remote.network.api.KtorApi
import uz.mobile.footzone.data.remote.network.api.impl.KtorApiImpl
import uz.mobile.footzone.data.remote.network.service.AuthApiService
import uz.mobile.footzone.data.remote.network.service.StadiumApiService
import uz.mobile.footzone.data.remote.network.service.impl.AuthApiServiceImpl
import uz.mobile.footzone.data.remote.network.service.impl.StadiumApiServiceImpl
import uz.mobile.footzone.data.settings.SettingsSource
import uz.mobile.footzone.data.settings.SettingsSourceImpl
import uz.mobile.footzone.data.remote.RemoteDataSource
import uz.mobile.footzone.data.remote.impl.RemoteDataSourceImpl
import uz.mobile.footzone.domain.repository.AuthRepository
import uz.mobile.footzone.domain.repository.StadiumRepository
import uz.mobile.footzone.data.repository.AuthRepositoryImpl
import uz.mobile.footzone.data.repository.StadiumRepositoryImpl
import uz.mobile.footzone.domain.usecase.AuthUseCase
import uz.mobile.footzone.domain.usecase.StadiumUseCase
import uz.mobile.footzone.domain.usecase.impl.AuthUseCaseImpl
import uz.mobile.footzone.domain.usecase.impl.StadiumUseCaseImpl

fun initKoin(additionalModules: List<Module> = emptyList()) = startKoin {
    modules(
        apiModule +
                networkModule +
                useCaseModule +
                platformModule +
                coreModule +
                additionalModules
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