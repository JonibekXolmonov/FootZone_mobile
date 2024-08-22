package uz.mobile.footzone.android.presentation.screens.auth.otp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.presentation.components.AppPrimaryButton
import uz.mobile.footzone.android.presentation.components.AppTopBar
import uz.mobile.footzone.presentation.auth.register.OTPErrorState
import uz.mobile.footzone.android.presentation.screens.auth.register.RegisterInputField
import uz.mobile.footzone.android.theme.MyApplicationTheme
import uz.mobile.footzone.android.theme.blue600
import uz.mobile.footzone.android.theme.neutral90
import uz.mobile.footzone.presentation.auth.otp.OTPSideEffects
import uz.mobile.footzone.presentation.auth.otp.OTPState
import uz.mobile.footzone.presentation.auth.otp.OTPUiEvents

@Composable
fun OTPValidationRoute(
    modifier: Modifier = Modifier,
    viewModel: OTPValidationViewModel = koinViewModel(),
    onBackPressed: () -> Unit,
    onNavigateToPasswordReset: () -> Unit,
    onOtpVerified: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val sideEffect by viewModel.sideEffect.collectAsStateWithLifecycle(OTPSideEffects.Nothing)

    OTPValidationScreen(
        modifier = modifier.fillMaxSize(),
        state = state,
        onVerifyOTP = {
            viewModel.onUiEvent(OTPUiEvents.VerifyOTP)
        },
        onOTPChanged = {
            viewModel.onUiEvent(OTPUiEvents.OTPChanged(it))
        },
        onResendOTP = {
            viewModel.onUiEvent(OTPUiEvents.ResendOTP)
        },
        onBackPressed = onBackPressed
    )

    when (sideEffect) {
        OTPSideEffects.NavigateToPasswordReset -> {
            onNavigateToPasswordReset()
        }

        OTPSideEffects.Nothing -> {}
    }
}

@Composable
fun OTPValidationScreen(
    modifier: Modifier,
    state: OTPState,
    onVerifyOTP: () -> Unit,
    onOTPChanged: (String) -> Unit,
    onResendOTP: () -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.forget_password),
                onBack = onBackPressed
            )
        },
        content = { padding ->
            val errorState = state.errorState.networkErrorState
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(top = 20.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = stringResource(R.string.enter_sent_otp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = neutral90,
                        textAlign = TextAlign.Center
                    )
                )

                RegisterInputField(
                    modifier = Modifier.padding(top = 16.dp),
                    value = state.otp,
                    onValueChange = onOTPChanged,
                    label = stringResource(R.string.otp),
                    hasError = errorState == OTPErrorState,
                    errorMessage = errorState.errorMessage,
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.resend_otp),
                        style = MaterialTheme.typography.bodyMedium.copy(color = blue600),
                        modifier = Modifier.clickable(onClick = onResendOTP)
                    )

                    AppPrimaryButton(
                        text = stringResource(R.string.set_new_password),
                        onClick = onVerifyOTP,
                        enabled = state.verifyOTPEnabled
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun PasswordRecoverScreenContentPr(modifier: Modifier = Modifier) {
    MyApplicationTheme {
        OTPValidationScreen(
            modifier = Modifier.fillMaxSize(),
            state = OTPState(),
            onOTPChanged = {}, onVerifyOTP = {}, onBackPressed = {}, onResendOTP = {})
    }
}
