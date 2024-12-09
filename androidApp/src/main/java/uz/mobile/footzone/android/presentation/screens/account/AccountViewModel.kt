package uz.mobile.footzone.android.presentation.screens.account

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.mobile.footzone.android.BuildConfig
import uz.mobile.footzone.domain.model.AccountUiModel
import uz.mobile.footzone.domain.model.UserType
import uz.mobile.footzone.presentation.account.AccountSideEffects
import uz.mobile.footzone.presentation.account.AccountScreenUiEvent
import uz.mobile.footzone.presentation.account.AccountState

class AccountViewModel(
) : ViewModel() {
    private val _state = MutableStateFlow(AccountState())
    val state: StateFlow<AccountState> = _state

    private val _sideEffect = MutableSharedFlow<AccountSideEffects>()
    val sideEffect: SharedFlow<AccountSideEffects> = _sideEffect

    fun onUiEvent(uiEvent: AccountScreenUiEvent) {
        when (uiEvent) {

            is AccountScreenUiEvent.Login -> {
                viewModelScope.launch {
                    _sideEffect.emit(AccountSideEffects.NavigateToLogin)
                }
            }

            is AccountScreenUiEvent.LogOut -> {
                _state.value = _state.value.copy(userType = UserType.UNAUTHORIZED)

            }

            is AccountScreenUiEvent.ChangeUserImage -> {
                viewModelScope.launch {
                    _sideEffect.emit(AccountSideEffects.OpenImagePicker)
                }
            }

            is AccountScreenUiEvent.UserName -> {

            }

            is AccountScreenUiEvent.UserNumber -> {

            }

            is AccountScreenUiEvent.Notifications -> {
                viewModelScope.launch {
                    _sideEffect.emit(AccountSideEffects.NavigateToNotifications)
                }
            }

            is AccountScreenUiEvent.LanguageChange -> {
                viewModelScope.launch {
                    _sideEffect.emit(AccountSideEffects.NavigateToLanguageChange)
                }
            }
        }
    }

    fun onImagePicked(uri: Uri) {
        val imagePath = uri.toString()
        _state.value = _state.value.copy(account = _state.value.account.copy(image = imagePath))
    }

    fun clearViewModel() {
        viewModelScope.launch {
            _state.value = AccountState(

            )
        }
    }

    init {
        viewModelScope.launch {
            val appVersion = BuildConfig.VERSION_NAME
            val account = AccountUiModel(
                id = 1,
                name = "Odilbek Rustamov",
                number = "+998917751779",
                image = "https://picsum.photos/536/354"
            )
            _state.value = _state.value.copy(appVersion = appVersion, account = account)
        }
    }
}