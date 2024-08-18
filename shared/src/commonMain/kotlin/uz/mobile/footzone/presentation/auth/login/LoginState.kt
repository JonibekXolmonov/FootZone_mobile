package uz.mobile.footzone.presentation.auth.login

import uz.mobile.footzone.common.Constants.EMPTY
import uz.mobile.footzone.presentation.auth.register.RegisterErrorState

data class LoginState(
    val isLoading: Boolean = false,
    val mobile: String = EMPTY,
    val password: String = EMPTY,
    val errorState: RegisterErrorState = RegisterErrorState(),
    val loginEnabled: Boolean = false
)