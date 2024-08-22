package uz.mobile.footzone.android.presentation.screens.auth.reset_password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import uz.mobile.footzone.android.presentation.screens.auth.register.RegisterInputField
import uz.mobile.footzone.android.theme.MyApplicationTheme
import uz.mobile.footzone.presentation.auth.password.PasswordSideEffects
import uz.mobile.footzone.presentation.auth.password.PasswordState
import uz.mobile.footzone.presentation.auth.password.PasswordUiEvents
import uz.mobile.footzone.presentation.auth.register.passwordEmptyErrorState
import uz.mobile.footzone.presentation.auth.register.passwordNotMatchErrorState

@Composable
fun ResetPasswordRoute(
    modifier: Modifier = Modifier,
    viewModel: PasswordViewModel = koinViewModel(),
    onBackPressed: () -> Unit,
    onNavigateToLogin: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val sideEffect by viewModel.sideEffect.collectAsStateWithLifecycle(PasswordSideEffects.Nothing)


    ResetPasswordScreen(
        modifier = modifier,
        state = state,
        onPasswordChanged = {
            viewModel.onUiEvent(PasswordUiEvents.PasswordChanged(it))
        },
        onRePasswordChanged = {
            viewModel.onUiEvent(PasswordUiEvents.RePasswordChanged(it))
        },
        resetPassword = {
            viewModel.onUiEvent(PasswordUiEvents.Reset)
        },
        onBackPressed = onBackPressed
    )

    when (sideEffect) {
        PasswordSideEffects.NavigateToLogin -> {
            onNavigateToLogin()
        }

        PasswordSideEffects.Nothing -> {}
    }
}

@Composable
fun ResetPasswordScreen(
    modifier: Modifier,
    state: PasswordState,
    onPasswordChanged: (String) -> Unit,
    onRePasswordChanged: (String) -> Unit,
    resetPassword: () -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.set_new_password),
                onBack = onBackPressed
            )
        },
        content = { padding ->
            val errorState = state.errorState.networkErrorState
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(top = 4.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                RegisterInputField(
                    modifier = Modifier.padding(top = 16.dp),
                    value = state.password,
                    onValueChange = onPasswordChanged,
                    label = stringResource(R.string.password),
                    hasError = errorState == passwordEmptyErrorState,
                    errorMessage = errorState.errorMessage,
                )

                RegisterInputField(
                    modifier = Modifier.padding(top = 16.dp),
                    value = state.rePassword,
                    onValueChange = onRePasswordChanged,
                    label = stringResource(R.string.repassword),
                    hasError = errorState == passwordNotMatchErrorState,
                    errorMessage = errorState.errorMessage,
                )


                AppPrimaryButton(
                    modifier = Modifier.align(Alignment.End),
                    text = stringResource(R.string.set),
                    onClick = resetPassword,
                    enabled = state.resetEnabled
                )
            }
        }
    )
}

@Preview
@Composable
fun ResetPasswordScreenContentPr(modifier: Modifier = Modifier) {
    MyApplicationTheme {
        ResetPasswordScreen(
            state = PasswordState(),
            onPasswordChanged = {},
            onRePasswordChanged = {},
            resetPassword = { },
            modifier = Modifier
        ) {

        }
    }
}