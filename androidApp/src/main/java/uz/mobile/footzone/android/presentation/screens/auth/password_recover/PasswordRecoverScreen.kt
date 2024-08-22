package uz.mobile.footzone.android.presentation.screens.auth.password_recover

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.presentation.components.AppPrimaryButton
import uz.mobile.footzone.android.presentation.components.AppTopBar
import uz.mobile.footzone.android.presentation.screens.auth.register.PhoneInput
import uz.mobile.footzone.presentation.auth.register.mobileEmptyErrorState
import uz.mobile.footzone.android.theme.MyApplicationTheme
import uz.mobile.footzone.android.theme.neutral90
import uz.mobile.footzone.presentation.auth.password.PasswordRecoverSideEffects
import uz.mobile.footzone.presentation.auth.password.PasswordRecoverUiEvents
import uz.mobile.footzone.presentation.auth.password.PasswordRecoveryState

@Composable
fun PasswordRecoverRoute(
    modifier: Modifier = Modifier,
    viewModel: PasswordRecoverViewModel = koinViewModel(),
    onBackPressed: () -> Unit,
    onNavigateToOTPValidation: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val sideEffect by viewModel.sideEffect.collectAsStateWithLifecycle(PasswordRecoverSideEffects.Nothing)

    PasswordRecoverScreen(
        modifier = modifier.fillMaxSize(),
        state = state,
        onMobileChanged = {
            viewModel.onUiEvent(PasswordRecoverUiEvents.MobileChanged(it))
        },
        onSendOTP = {
            viewModel.onUiEvent(PasswordRecoverUiEvents.SendOTP)
        },
        onBackPressed = onBackPressed
    )

    when (sideEffect) {
        PasswordRecoverSideEffects.NavigateToOTPValidation -> {
            onNavigateToOTPValidation()
        }

        PasswordRecoverSideEffects.Nothing -> {

        }
    }
}

@Composable
fun PasswordRecoverScreen(
    modifier: Modifier,
    state: PasswordRecoveryState,
    onSendOTP: () -> Unit,
    onMobileChanged: (String) -> Unit,
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
                    text = stringResource(R.string.enter_registred_phone),
                    style = MaterialTheme.typography.bodyMedium.copy(color = neutral90)
                )

                PhoneInput(
                    modifier = Modifier.padding(top = 16.dp),
                    value = state.mobile,
                    onValueChange = onMobileChanged,
                    label = stringResource(R.string.mobile),
                    hasError = errorState == mobileEmptyErrorState,
                    errorMessage = errorState.errorMessage,
                )

                AppPrimaryButton(
                    modifier = Modifier.align(Alignment.End),
                    text = stringResource(R.string.send_otp),
                    onClick = onSendOTP,
                    enabled = state.sendOTPEnabled
                )
            }
        }
    )
}

@Preview
@Composable
fun PasswordRecoverScreenContentPr(modifier: Modifier = Modifier) {
    MyApplicationTheme {
        PasswordRecoverScreen(
            modifier = Modifier.fillMaxSize(),
            state = PasswordRecoveryState(),
            onSendOTP = { }, {}) {

        }
    }
}
