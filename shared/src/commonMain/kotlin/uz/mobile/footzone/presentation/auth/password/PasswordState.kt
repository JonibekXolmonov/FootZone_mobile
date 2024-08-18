package uz.mobile.footzone.presentation.auth.password

import uz.mobile.footzone.common.Constants.EMPTY
import uz.mobile.footzone.presentation.auth.register.RegisterErrorState

data class PasswordState(
    val isLoading: Boolean = false,
    var password: String = EMPTY,
    var rePassword: String = EMPTY,
    val errorState: RegisterErrorState = RegisterErrorState(),
    val isPasswordSetSuccessfully: Boolean = false,
    val resetEnabled: Boolean = false
)