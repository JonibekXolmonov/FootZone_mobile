package uz.mobile.footzone.presentation.auth.password

import uz.mobile.footzone.common.Constants.EMPTY
import uz.mobile.footzone.presentation.auth.register.RegisterErrorState

data class PasswordRecoveryState(
    val isLoading: Boolean = false,
    var mobile: String = EMPTY,
    val errorState: RegisterErrorState = RegisterErrorState(),
    val sendOTPEnabled: Boolean = false
)