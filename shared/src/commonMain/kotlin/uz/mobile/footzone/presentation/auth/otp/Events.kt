package uz.mobile.footzone.presentation.auth.otp


sealed class OTPUiEvents {
    data class MobileChanged(val mobile: String) : OTPUiEvents()
    data class OTPChanged(val otp: String) : OTPUiEvents()
    data object VerifyOTP : OTPUiEvents()
    data object ResendOTP : OTPUiEvents()
}

sealed class OTPSideEffects {
    data object NavigateToPasswordReset : OTPSideEffects()
    data object Nothing : OTPSideEffects()
}