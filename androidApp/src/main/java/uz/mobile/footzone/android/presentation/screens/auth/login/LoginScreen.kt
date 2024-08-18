package uz.mobile.footzone.android.presentation.screens.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
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
import org.koin.androidx.compose.koinViewModel
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.presentation.components.AppPrimaryButton
import uz.mobile.footzone.android.presentation.components.AppTopBar
import uz.mobile.footzone.android.presentation.screens.auth.register.PhoneInput
import uz.mobile.footzone.android.presentation.screens.auth.register.RegisterInputField
import uz.mobile.footzone.presentation.auth.register.mobileEmptyErrorState
import uz.mobile.footzone.presentation.auth.register.passwordEmptyErrorState
import uz.mobile.footzone.android.theme.MyApplicationTheme
import uz.mobile.footzone.android.theme.blue600
import uz.mobile.footzone.android.theme.neutral40
import uz.mobile.footzone.android.theme.neutral90
import uz.mobile.footzone.presentation.auth.login.LoginSideEffect
import uz.mobile.footzone.presentation.auth.login.LoginState
import uz.mobile.footzone.presentation.auth.login.LoginUiEvent

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {

    val viewModel: LoginViewModel = koinViewModel()
    val loginState by viewModel.state.collectAsState()
    val sideEffect by viewModel.sideEffect.collectAsState(LoginSideEffect.Nothing)

    LoginScreenContent(
        modifier = Modifier.fillMaxSize(),
        loginState = loginState,
        onPasswordChanged = {
            viewModel.onUiEvent(
                LoginUiEvent.PasswordChanged(it)
            )
        },
        onMobileChanged = {
            viewModel.onUiEvent(
                LoginUiEvent.MobileChanged(it)
            )
        },
        login = {
            viewModel.onUiEvent(LoginUiEvent.Login)
        },
        navigateToRegister = {
            viewModel.onUiEvent(LoginUiEvent.CreateAccount)
        },
        navigateToResetPassword = {
            viewModel.onUiEvent(LoginUiEvent.ResetPassword)
        },
        onBackPressed = {
//            navigator.pop()
        }
    )

    when (sideEffect) {
        LoginSideEffect.ForgetPassword -> {
//            navigator.push(PasswordRecoverScreen())
        }

        LoginSideEffect.LoginSuccess -> {
//            navigator.push(MainScreen())
        }

        LoginSideEffect.NavigateToRegister -> {
//            navigator.push(RegistrationScreen())
        }

        LoginSideEffect.Nothing -> {}
        LoginSideEffect.NavigateToResetPassword -> {
//            navigator.push(PasswordRecoverScreen())
        }
    }
}


@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    loginState: LoginState,
    onMobileChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    login: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToResetPassword: () -> Unit,
    onBackPressed: () -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.enter),
                onBack = onBackPressed
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {

                LoginInputs(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 20.dp),
                    state = loginState,
                    onPasswordChanged = onPasswordChanged,
                    onMobileChanged = onMobileChanged
                )

                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.forget_password),
                        style = MaterialTheme.typography.bodyMedium.copy(color = blue600),
                        modifier = Modifier.clickable(onClick = navigateToResetPassword)
                    )
                    AppPrimaryButton(
                        text = stringResource(R.string.enter),
                        onClick = login,
                        enabled = loginState.loginEnabled
                    )
                }

                Spacer(Modifier.weight(1f))

                HorizontalDivider(color = neutral40, modifier = Modifier.fillMaxWidth())
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 22.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.no_account),
                        style = MaterialTheme.typography.bodyMedium.copy(color = neutral90)
                    )
                    Text(
                        text = stringResource(R.string.enter_register),
                        modifier = Modifier.clickable(onClick = navigateToRegister),
                        style = MaterialTheme.typography.bodyMedium.copy(color = blue600)
                    )
                }
            }
        }
    )
}

@Composable
fun LoginInputs(
    modifier: Modifier = Modifier,
    state: LoginState,
    onPasswordChanged: (String) -> Unit,
    onMobileChanged: (String) -> Unit
) {
    val errorState = state.errorState.networkErrorState

    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        PhoneInput(
            value = state.mobile,
            onValueChange = onMobileChanged,
            label = stringResource(R.string.mobile),
            hasError = errorState == mobileEmptyErrorState,
            errorMessage = errorState.errorMessage,
        )
        RegisterInputField(
            value = state.password,
            onValueChange = onPasswordChanged,
            label = stringResource(R.string.password),
            hasError = errorState == passwordEmptyErrorState,
            errorMessage = errorState.errorMessage
        )
    }
}

@Preview
@Composable
fun LoginScreenContentPr(modifier: Modifier = Modifier) {
    MyApplicationTheme {

        LoginScreenContent(
            modifier = modifier.fillMaxSize(),
            loginState = LoginState(),
            onMobileChanged = {}, {}, {}, {}, {}) {

        }
    }
}