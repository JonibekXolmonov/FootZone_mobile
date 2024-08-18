package uz.mobile.footzone.android.presentation.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.mobile.footzone.presentation.auth.register.RegisterErrorState
import uz.mobile.footzone.presentation.auth.register.mobileEmptyErrorState
import uz.mobile.footzone.presentation.auth.register.passwordEmptyErrorState
import uz.mobile.footzone.domain.usecase.AuthUseCase
import uz.mobile.footzone.presentation.auth.login.LoginSideEffect
import uz.mobile.footzone.presentation.auth.login.LoginState
import uz.mobile.footzone.presentation.auth.login.LoginUiEvent

class LoginViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val _sideEffect = MutableSharedFlow<LoginSideEffect>()
    val sideEffect: SharedFlow<LoginSideEffect> = _sideEffect

    fun onUiEvent(loginUiEvent: LoginUiEvent) {
        when (loginUiEvent) {
            is LoginUiEvent.ForgetPassword -> {
                viewModelScope.launch {
                    _sideEffect.emit(LoginSideEffect.ForgetPassword)
                }
            }

            LoginUiEvent.Login -> {
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    _state.value = _state.value.copy(isLoading = true)
                    loginUser()
                }
            }

            is LoginUiEvent.MobileChanged -> {
                _state.value = _state.value.copy(
                    mobile = loginUiEvent.phone.take(13)
                )
            }

            is LoginUiEvent.PasswordChanged -> {
                _state.value = _state.value.copy(
                    password = loginUiEvent.password
                )
            }

            LoginUiEvent.CreateAccount -> {
                viewModelScope.launch {
                    _sideEffect.emit(LoginSideEffect.NavigateToRegister)
                }
            }

            LoginUiEvent.ResetPassword -> {
                viewModelScope.launch {
                    _sideEffect.emit(LoginSideEffect.NavigateToResetPassword)
                }
            }
        }

        val inputsValid = inputsValid()
        _state.value = state.value.copy(loginEnabled = inputsValid)
    }

    private fun validateInputs(): Boolean {
        val mobileString = state.value.mobile.trim()
        val password = state.value.password.trim()

        return when {

            mobileString.length < 9 -> {
                _state.value = state.value.copy(
                    errorState = RegisterErrorState(
                        networkErrorState = mobileEmptyErrorState
                    )
                )
                false
            }

            password.isEmpty() -> {
                _state.value = state.value.copy(
                    errorState = RegisterErrorState(
                        networkErrorState = passwordEmptyErrorState
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
        val password = state.value.password.trim()

        return mobileString.isNotBlank() && password.isNotBlank()
    }

    private fun loginUser() {
        viewModelScope.launch {
            _sideEffect.emit(LoginSideEffect.LoginSuccess)
        }
    }

    fun clearViewModel() {
        viewModelScope.launch {
            _state.value = LoginState(
                mobile = state.value.mobile,
                password = state.value.password,
            )
        }
    }
}