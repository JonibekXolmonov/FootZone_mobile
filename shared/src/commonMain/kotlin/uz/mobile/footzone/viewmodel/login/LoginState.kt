package uz.mobile.footzone.viewmodel.login

import uz.mobile.footzone.common.Constants.EMPTY
import uz.mobile.footzone.common.Constants.ZERO
import uz.mobile.footzone.common.ErrorState
import uz.mobile.footzone.model.UserType

data class LoginState(
    var name: String = EMPTY,
    var surname: String = EMPTY,
    var mobile: String = EMPTY,
    var password: String = EMPTY,
    var rePassword: String = EMPTY,
    var userType: UserType = UserType.USER,
    var registerEnabled: Boolean = false,
    val errorState: LoginErrorState = LoginErrorState(),
    val isLoginSuccessful: Boolean = false,
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
        errorState = LoginErrorState(),
        isLoginSuccessful = false,
        isLoading = false,
    )
}


/**
 * Error state in login holding respective
 * text field validation errors
 */
data class LoginErrorState(
    val networkErrorState: ErrorState = ErrorState(),
) {
    constructor() : this(
        networkErrorState = ErrorState()
    )
}