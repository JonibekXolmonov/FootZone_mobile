package uz.mobile.footzone.presentation.auth.password


sealed class PasswordRecoverUiEvents {
    data class MobileChanged(val mobile: String) : PasswordRecoverUiEvents()
    data object SendOTP : PasswordRecoverUiEvents()
}

sealed class PasswordRecoverSideEffects {

    data object NavigateToOTPValidation : PasswordRecoverSideEffects()
    data object Nothing : PasswordRecoverSideEffects()
}