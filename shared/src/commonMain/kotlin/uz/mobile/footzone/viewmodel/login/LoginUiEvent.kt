package uz.mobile.footzone.viewmodel.login

import uz.mobile.footzone.model.UserType


sealed class LoginUiEvent {
    data class UserTypeChanged(val inputValue: UserType) : LoginUiEvent()
    data class NameChanged(val inputValue: String) : LoginUiEvent()
    data class SurnameChanged(val inputValue: String) : LoginUiEvent()
    data class MobileChanged(val inputValue: String) : LoginUiEvent()
    data class PasswordChanged(val inputValue: String) : LoginUiEvent()
    data class RePasswordChanged(val inputValue: String) : LoginUiEvent()
    data object Register : LoginUiEvent()
}