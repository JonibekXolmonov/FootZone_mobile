package uz.mobile.footzone.android.presentation.screens.auth.password_recover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.mobile.footzone.presentation.auth.register.RegisterErrorState
import uz.mobile.footzone.presentation.auth.register.mobileEmptyErrorState
import uz.mobile.footzone.domain.usecase.AuthUseCase
import uz.mobile.footzone.presentation.auth.password.PasswordRecoverSideEffects
import uz.mobile.footzone.presentation.auth.password.PasswordRecoverUiEvents
import uz.mobile.footzone.presentation.auth.password.PasswordRecoveryState

class PasswordRecoverViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    private val _state = MutableStateFlow(PasswordRecoveryState())
    val state: StateFlow<PasswordRecoveryState> = _state

    private val _sideEffect = MutableSharedFlow<PasswordRecoverSideEffects>()
    val sideEffect: SharedFlow<PasswordRecoverSideEffects> = _sideEffect

    fun onUiEvent(event: PasswordRecoverUiEvents) {
        when (event) {
            is PasswordRecoverUiEvents.MobileChanged -> {
                _state.value = state.value.copy(
                    mobile = event.mobile.take(13)
                )
            }

            PasswordRecoverUiEvents.SendOTP -> {
                val inputsValidated = validateInputs()

                if (inputsValidated) {
                    _state.value = _state.value.copy(isLoading = true)
                    sentOTP(_state.value.mobile)
                }
            }
        }
        val inputsValid = inputsValid()
        _state.value = state.value.copy(sendOTPEnabled = inputsValid)
    }

    private fun validateInputs(): Boolean {
        val mobileString = state.value.mobile.trim()

        return when {

            mobileString.length < 9 -> {
                _state.value = state.value.copy(
                    errorState = RegisterErrorState(
                        networkErrorState = mobileEmptyErrorState
                    )
                )
                false
            }

            // No errors
            else -> {
                // Set default error state
                _state.value = state.value.copy(errorState = RegisterErrorState())
                true
            }
        }
    }

    private fun inputsValid(): Boolean {
        val mobileString = state.value.mobile.trim()

        return mobileString.isNotBlank()
    }

    private fun sentOTP(mobile: String) {
        viewModelScope.launch {
            authUseCase.sendOTP(phone = mobile)

            _state.value = _state.value.copy(isLoading = false)
            _sideEffect.emit(PasswordRecoverSideEffects.NavigateToOTPValidation)
        }
    }
}