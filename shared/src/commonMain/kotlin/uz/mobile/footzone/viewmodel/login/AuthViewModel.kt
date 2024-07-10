package uz.mobile.footzone.viewmodel.login

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import uz.mobile.footzone.common.ErrorState
import uz.mobile.footzone.usecase.AuthUseCase
import uz.mobile.footzone.util.CoroutineViewModel

class AuthViewModel : CoroutineViewModel(), KoinComponent {

    private val authUseCase: AuthUseCase by inject()

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onUiEvent(loginUiEvent: LoginUiEvent) {
        when (loginUiEvent) {
            is LoginUiEvent.UserTypeChanged -> {
                _state.value = state.value.copy(
                    userType = loginUiEvent.inputValue,
                )
            }

            is LoginUiEvent.MobileChanged -> {
                if (loginUiEvent.inputValue.length <= 13) {
                    _state.value = state.value.copy(
                        mobile = loginUiEvent.inputValue
                    )
                } else {
                    _state.value = state.value.copy(
                        mobile = loginUiEvent.inputValue.take(13)
                    )
                }
            }

            is LoginUiEvent.NameChanged -> {
                _state.value = state.value.copy(
                    name = loginUiEvent.inputValue
                )
            }

            is LoginUiEvent.SurnameChanged -> {
                _state.value = state.value.copy(
                    surname = loginUiEvent.inputValue
                )
            }

            is LoginUiEvent.PasswordChanged -> {
                _state.value = state.value.copy(
                    password = loginUiEvent.inputValue
                )
            }

            is LoginUiEvent.RePasswordChanged -> {
                _state.value = state.value.copy(
                    rePassword = loginUiEvent.inputValue
                )
            }

            LoginUiEvent.Register -> {
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
                    errorState = LoginErrorState(
                        networkErrorState = nameEmptyErrorState
                    )
                )
                false
            }

            surname.isEmpty() -> {
                _state.value = state.value.copy(
                    errorState = LoginErrorState(
                        networkErrorState = surnameEmptyErrorState
                    )
                )
                false
            }

            mobileString.length < 9 -> {
                _state.value = state.value.copy(
                    errorState = LoginErrorState(
                        networkErrorState = mobileEmptyErrorState
                    )
                )
                false
            }

            password.isEmpty() -> {
                _state.value = state.value.copy(
                    errorState = LoginErrorState(
                        networkErrorState = passwordEmptyErrorState
                    )
                )
                false
            }

            rePassword != password -> {
                _state.value = state.value.copy(
                    errorState = LoginErrorState(
                        networkErrorState = passwordNotMatchErrorState
                    )
                )
                false
            }

            // No errors
            else -> {
                // Set default error state
                _state.value = state.value.copy(errorState = LoginErrorState())
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
        coroutineScope.launch {

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
                    _state.value = LoginState(
                        mobile = state.value.mobile,
                        isLoginSuccessful = true,
                        isLoading = false
                    )
                }
                .onFailure { throwable ->
                    _state.value = state.value.copy(
                        isLoading = false,
                        errorState = LoginErrorState(
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
        coroutineScope.launch {
            _state.value = LoginState(
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

    fun observeSignUp(onChange: (LoginState) -> Unit) {
        state.onEach {
            onChange(it)
        }.launchIn(coroutineScope)
    }
}