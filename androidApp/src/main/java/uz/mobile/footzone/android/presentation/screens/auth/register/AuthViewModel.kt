package uz.mobile.footzone.android.presentation.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.mobile.footzone.common.ErrorState
import uz.mobile.footzone.domain.usecase.AuthUseCase
import uz.mobile.footzone.presentation.auth.register.RegisterErrorState
import uz.mobile.footzone.presentation.auth.register.RegisterState
import uz.mobile.footzone.presentation.auth.register.RegisterUiEvent
import uz.mobile.footzone.presentation.auth.register.mobileEmptyErrorState
import uz.mobile.footzone.presentation.auth.register.nameEmptyErrorState
import uz.mobile.footzone.presentation.auth.register.passwordEmptyErrorState
import uz.mobile.footzone.presentation.auth.register.passwordNotMatchErrorState
import uz.mobile.footzone.presentation.auth.register.surnameEmptyErrorState

class AuthViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    fun onUiEvent(registerUiEvent: RegisterUiEvent) {
        when (registerUiEvent) {
            is RegisterUiEvent.UserTypeChanged -> {
                _state.value = state.value.copy(
                    userType = registerUiEvent.inputValue,
                )
            }

            is RegisterUiEvent.MobileChanged -> {
                _state.value = state.value.copy(
                    mobile = registerUiEvent.inputValue.take(13)
                )
            }

            is RegisterUiEvent.NameChanged -> {
                _state.value = state.value.copy(
                    name = registerUiEvent.inputValue
                )
            }

            is RegisterUiEvent.SurnameChanged -> {
                _state.value = state.value.copy(
                    surname = registerUiEvent.inputValue
                )
            }

            is RegisterUiEvent.PasswordChanged -> {
                _state.value = state.value.copy(
                    password = registerUiEvent.inputValue
                )
            }

            is RegisterUiEvent.RePasswordChanged -> {
                _state.value = state.value.copy(
                    rePassword = registerUiEvent.inputValue
                )
            }

            RegisterUiEvent.Register -> {
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    _state.value = state.value.copy(isLoading = true)

                    signUp()
                }
            }
        }

        val inputsValid = inputsValid()
        _state.value = state.value.copy(registerEnabled = inputsValid)
    }

    private fun validateInputs(): Boolean {
        val mobileString = state.value.mobile.trim()
        val password = state.value.password.trim()
        val rePassword = state.value.rePassword.trim()
        val name = state.value.name.trim()
        val surname = state.value.surname.trim()

        return when {

            name.isEmpty() -> {
                _state.value = state.value.copy(
                    errorState = RegisterErrorState(
                        networkErrorState = nameEmptyErrorState
                    )
                )
                false
            }

            surname.isEmpty() -> {
                _state.value = state.value.copy(
                    errorState = RegisterErrorState(
                        networkErrorState = surnameEmptyErrorState
                    )
                )
                false
            }

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
        val mobileString = state.value.mobile.trim()
        val password = state.value.password.trim()
        val rePassword = state.value.rePassword.trim()
        val name = state.value.name.trim()
        val surname = state.value.surname.trim()

        return mobileString.isNotBlank() && password.isNotBlank() && rePassword.isNotBlank() && name.isNotBlank() && surname.isNotBlank()
    }

    private fun signUp() {
        viewModelScope.launch {

            val response = authUseCase.signUp(
                name = state.value.name,
                surname = state.value.surname,
                phone = state.value.mobile,
                password = state.value.password,
                rePassword = state.value.rePassword,
                userType = state.value.userType,
            )

            response
                .onSuccess {
                    _state.value = RegisterState(
                        mobile = state.value.mobile,
                        isRegisterSuccessful = true,
                        isLoading = false
                    )
                }
                .onFailure { throwable ->
                    _state.value = state.value.copy(
                        isLoading = false,
                        errorState = RegisterErrorState(
                            networkErrorState = ErrorState(
                                hasError = true,
                                errorMessage = throwable.message.orEmpty()
                            )
                        )
                    )
                }
        }
    }

    fun clearViewModel() {
        viewModelScope.launch {
            _state.value = RegisterState(
                mobile = state.value.mobile,
                name = state.value.name,
                surname = state.value.surname,
                password = state.value.password,
                rePassword = state.value.rePassword,
                registerEnabled = state.value.registerEnabled,
                userType = state.value.userType
            )
        }
    }
}