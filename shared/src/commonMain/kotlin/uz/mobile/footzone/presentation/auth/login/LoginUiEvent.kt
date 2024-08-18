package uz.mobile.footzone.presentation.auth.login

sealed class LoginUiEvent {
    data class MobileChanged(val phone: String) : LoginUiEvent()
    data class PasswordChanged(val password: String) : LoginUiEvent()
    data object Login : LoginUiEvent()
    data object ForgetPassword : LoginUiEvent()
    data object CreateAccount : LoginUiEvent()
    data object ResetPassword : LoginUiEvent()
}

sealed class LoginSideEffect {
    data object ForgetPassword : LoginSideEffect()
    data object LoginSuccess : LoginSideEffect()
    data object NavigateToRegister : LoginSideEffect()
    data object NavigateToResetPassword : LoginSideEffect()
    data object Nothing : LoginSideEffect()
}