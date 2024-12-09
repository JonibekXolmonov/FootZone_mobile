package uz.mobile.footzone.presentation.account

sealed class AccountScreenUiEvent {
    data object Login : AccountScreenUiEvent()
    data object LogOut : AccountScreenUiEvent()
    data object ChangeUserImage : AccountScreenUiEvent()
    data object UserName : AccountScreenUiEvent()
    data object UserNumber : AccountScreenUiEvent()
    data object LanguageChange : AccountScreenUiEvent()
    data object Notifications : AccountScreenUiEvent()
}

sealed class AccountSideEffects {
    data object NavigateToLogin : AccountSideEffects()
    data object NavigateToNotifications : AccountSideEffects()
    data object NavigateToLanguageChange : AccountSideEffects()
    data object OpenImagePicker : AccountSideEffects()
    data object Nothing : AccountSideEffects()
}