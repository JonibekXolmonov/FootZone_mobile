package uz.mobile.footzone.android.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import uz.mobile.footzone.android.presentation.screens.auth.login.LoginViewModel
import uz.mobile.footzone.android.presentation.screens.auth.otp.OTPValidationViewModel
import uz.mobile.footzone.android.presentation.screens.auth.password_recover.PasswordRecoverViewModel
import uz.mobile.footzone.android.presentation.screens.auth.register.AuthViewModel
import uz.mobile.footzone.android.presentation.screens.auth.reset_password.PasswordViewModel
import uz.mobile.footzone.android.presentation.screens.main.MainViewModel

internal val appModule = module {
    // View Models
    viewModel { MainViewModel(get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { PasswordRecoverViewModel(get()) }
    viewModel { OTPValidationViewModel(get()) }
    viewModel { PasswordViewModel(get()) }
}