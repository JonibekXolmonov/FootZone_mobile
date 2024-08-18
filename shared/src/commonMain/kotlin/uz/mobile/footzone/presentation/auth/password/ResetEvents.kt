package uz.mobile.footzone.presentation.auth.password


sealed class PasswordUiEvents {
    data class PasswordChanged(val password: String) : PasswordUiEvents()
    data class RePasswordChanged(val rePassword: String) : PasswordUiEvents()
    data object Reset : PasswordUiEvents()
}

sealed class PasswordSideEffects {
    data object NavigateToLogin : PasswordSideEffects()
    data object Nothing : PasswordSideEffects()
}