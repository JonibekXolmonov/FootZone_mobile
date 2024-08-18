package uz.mobile.footzone.android.presentation.screens.auth.reset_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.mobile.footzone.presentation.auth.register.RegisterErrorState
import uz.mobile.footzone.presentation.auth.register.passwordEmptyErrorState
import uz.mobile.footzone.presentation.auth.register.passwordNotMatchErrorState
import uz.mobile.footzone.domain.usecase.AuthUseCase
import uz.mobile.footzone.presentation.auth.password.PasswordSideEffects
import uz.mobile.footzone.presentation.auth.password.PasswordState
import uz.mobile.footzone.presentation.auth.password.PasswordUiEvents

class PasswordViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    private val _state = MutableStateFlow(PasswordState())
    val state: StateFlow<PasswordState> = _state

    private val _sideEffect = MutableSharedFlow<PasswordSideEffects>()
    val sideEffect: SharedFlow<PasswordSideEffects> = _sideEffect


    fun onUiEvent(passwordUiEvent: PasswordUiEvents) {
        when (passwordUiEvent) {

            is PasswordUiEvents.PasswordChanged -> {
                _state.value = state.value.copy(
                    password = passwordUiEvent.password
                )
            }

            is PasswordUiEvents.RePasswordChanged -> {
                _state.value = state.value.copy(
                    rePassword = passwordUiEvent.rePassword
                )
            }

            PasswordUiEvents.Reset -> {
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    _state.value = state.value.copy(isLoading = true)

                    reset()
                }
            }
        }

        val inputsValid = inputsValid()
        _state.value = state.value.copy(resetEnabled = inputsValid)
    }

    private fun validateInputs(): Boolean {
        val password = state.value.password.trim()
        val rePassword = state.value.rePassword.trim()

        return when {
            password.isEmpty() -> {
                _state.value = state.value.copy(
                    errorState = RegisterErrorState(
                        networkErrorState = passwordEmptyErrorState
                    )
                )
                false
            }

            rePassword != password -> {
                _state.value = state.value.copy(
                    errorState = RegisterErrorState(
                        networkErrorState = passwordNotMatchErrorState
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
        val password = state.value.password.trim()
        val rePassword = state.value.rePassword.trim()

        return password.isNotBlank() && rePassword.isNotBlank()
    }

    private fun reset(){
        viewModelScope.launch {
            authUseCase.resetPassword()

            _state.value = _state.value.copy(isLoading = false)
            _sideEffect.emit(PasswordSideEffects.NavigateToLogin)
        }
    }
}