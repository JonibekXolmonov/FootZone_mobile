package uz.mobile.footzone.presentation.auth.otp

import uz.mobile.footzone.common.Constants.EMPTY
import uz.mobile.footzone.presentation.auth.register.RegisterErrorState

data class OTPState(
    val isLoading: Boolean = false,
    var mobile: String = EMPTY,
    var otp: String = EMPTY,
    val errorState: RegisterErrorState = RegisterErrorState(),
    val verifyOTPEnabled: Boolean = false
)