package uz.mobile.footzone.viewmodel.login

import uz.mobile.footzone.common.ErrorState

val mobileEmptyErrorState = ErrorState(
    hasError = true,
    errorMessage = "Phone number is not verify"
)

val nameEmptyErrorState = ErrorState(
    hasError = true,
    errorMessage = "Name is empty"
)

val surnameEmptyErrorState = ErrorState(
    hasError = true,
    errorMessage = "Surname is empty"
)

val passwordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessage = "Password is empty"
)

val rePasswordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessage = "rePassword is empty"
)

val passwordNotMatchErrorState = ErrorState(
    hasError = true,
    errorMessage = "Passwords does not match"
)
