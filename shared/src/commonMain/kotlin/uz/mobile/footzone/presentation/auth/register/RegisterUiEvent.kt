package uz.mobile.footzone.presentation.auth.register

import uz.mobile.footzone.domain.model.UserType


sealed class RegisterUiEvent {
    data class UserTypeChanged(val inputValue: UserType) : RegisterUiEvent()
    data class NameChanged(val inputValue: String) : RegisterUiEvent()
    data class SurnameChanged(val inputValue: String) : RegisterUiEvent()
    data class MobileChanged(val inputValue: String) : RegisterUiEvent()
    data class PasswordChanged(val inputValue: String) : RegisterUiEvent()
    data class RePasswordChanged(val inputValue: String) : RegisterUiEvent()
    data object Register : RegisterUiEvent()
}