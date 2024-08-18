package uz.mobile.footzone.presentation.auth.register

import uz.mobile.footzone.common.Constants.EMPTY
import uz.mobile.footzone.common.ErrorState
import uz.mobile.footzone.domain.model.UserType

data class RegisterState(
    var name: String = EMPTY,
    var surname: String = EMPTY,
    var mobile: String = EMPTY,
    var password: String = EMPTY,
    var rePassword: String = EMPTY,
    var userType: UserType = UserType.USER,
    var registerEnabled: Boolean = false,
    val errorState: RegisterErrorState = RegisterErrorState(),
    val isRegisterSuccessful: Boolean = false,
    val isLoading: Boolean = false
) {
    constructor() : this(
        name = EMPTY,
        surname = EMPTY,
        mobile = EMPTY,
        password = EMPTY,
        rePassword = EMPTY,
        userType = UserType.USER,
        registerEnabled = false,
        errorState = RegisterErrorState(),
        isRegisterSuccessful = false,
        isLoading = false,
    )
}


/**
 * Error state in login holding respective
 * text field validation errors
 */
data class RegisterErrorState(
    val networkErrorState: ErrorState = ErrorState(),
) {
    constructor() : this(
        networkErrorState = ErrorState()
    )
}