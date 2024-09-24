package uz.mobile.footzone.android.di

import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import uz.mobile.footzone.android.data.DefaultLocationClient
import uz.mobile.footzone.android.data.LocationClient
import uz.mobile.footzone.android.presentation.screens.auth.login.LoginViewModel
import uz.mobile.footzone.android.presentation.screens.auth.otp.OTPValidationViewModel
import uz.mobile.footzone.android.presentation.screens.auth.password_recover.PasswordRecoverViewModel
import uz.mobile.footzone.android.presentation.screens.auth.register.AuthViewModel
import uz.mobile.footzone.android.presentation.screens.auth.reset_password.PasswordViewModel
import uz.mobile.footzone.android.presentation.screens.main.MainViewModel
import uz.mobile.footzone.android.presentation.screens.schedule.ScheduleViewModel

internal val appModule = module {
    // View Models
    viewModel { MainViewModel(get(), get()) }
    viewModel { ScheduleViewModel(get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { PasswordRecoverViewModel(get()) }
    viewModel { OTPValidationViewModel(get(), get()) }
    viewModel { PasswordViewModel(get()) }
}

val coreModule = module {
    single<FusedLocationProviderClient> {
        LocationServices.getFusedLocationProviderClient(
            androidContext()
        )
    }

    single<LocationManager> { androidContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager }

    single<LocationClient> { DefaultLocationClient(androidContext(), get(), get()) }
}