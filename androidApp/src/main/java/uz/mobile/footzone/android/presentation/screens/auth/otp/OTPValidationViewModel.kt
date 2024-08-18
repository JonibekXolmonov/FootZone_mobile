package uz.mobile.footzone.android.presentation.screens.auth.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.mobile.footzone.presentation.auth.register.OTPErrorState
import uz.mobile.footzone.presentation.auth.register.RegisterErrorState
import uz.mobile.footzone.domain.usecase.AuthUseCase
import uz.mobile.footzone.presentation.auth.otp.OTPSideEffects
import uz.mobile.footzone.presentation.auth.otp.OTPState
import uz.mobile.footzone.presentation.auth.otp.OTPUiEvents

class OTPValidationViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    private val _state = MutableStateFlow(OTPState())
    val state: StateFlow<OTPState> = _state

    private val _sideEffect = MutableSharedFlow<OTPSideEffects>()
    val sideEffect: SharedFlow<OTPSideEffects> = _sideEffect

    fun onUiEvent(event: OTPUiEvents) {
        when (event) {
            is OTPUiEvents.MobileChanged -> {
                _state.value = state.value.copy(
                    mobile = event.mobile.take(13)
                )
            }

            OTPUiEvents.VerifyOTP -> {
                val inputsValidated = validateInputs()

                if (inputsValidated) {
                    _state.value = _state.value.copy(isLoading = true)
                    verifyOTP(_state.value.mobile, _state.value.otp)
                }
            }

            is OTPUiEvents.OTPChanged -> {
                _state.value = _state.value.copy(otp = event.otp.take(6))
            }

            OTPUiEvents.ResendOTP -> {
                _state.value = OTPState()
                resendOTP(_state.value.mobile)
            }
        }
        val inputsValid = inputsValid()
        _state.value = state.value.copy(verifyOTPEnabled = inputsValid)
    }

    private fun validateInputs(): Boolean {
        val otp = state.value.otp.trim()

        return when {

            otp.length < 6 -> {
                _state.value = state.value.copy(
                    errorState = RegisterErrorState(
                        networkErrorState = OTPErrorState
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
        val otp = state.value.otp.trim()

        return otp.isNotBlank()
    }

    private fun verifyOTP(mobile: String, otp: String) {
        viewModelScope.launch {
            authUseCase.verifyOTP(phone = mobile, otp = otp)

            _state.value = _state.value.copy(isLoading = false)
            _sideEffect.emit(OTPSideEffects.NavigateToPasswordReset)
        }
    }

    private fun resendOTP(mobile: String) {
        viewModelScope.launch {
            authUseCase.sendOTP(phone = mobile)

            _state.value = _state.value.copy(isLoading = false)
        }
    }
}