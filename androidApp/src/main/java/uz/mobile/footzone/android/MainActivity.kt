package uz.mobile.footzone.android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import uz.mobile.footzone.viewmodel.login.AuthViewModel
import uz.mobile.footzone.viewmodel.login.LoginState
import uz.mobile.footzone.viewmodel.login.LoginUiEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {

                val viewModel: AuthViewModel = koinViewModel()
                val uiState by viewModel.state.collectAsState()
                val context = LocalContext.current

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when {
                        uiState.isLoading -> {
                            Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                        }

                        uiState.isLoginSuccessful -> {
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                        }

                        uiState.errorState.networkErrorState.hasError -> {
                            Toast.makeText(
                                context,
                                uiState.errorState.networkErrorState.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    DemoScreenContent(
                        state = uiState,
                        onMobileChanged = { mobile ->
                            viewModel.onUiEvent(
                                loginUiEvent = LoginUiEvent.MobileChanged(mobile)
                            )
                        },
                        onNameChanged = { name ->
                            viewModel.onUiEvent(
                                loginUiEvent = LoginUiEvent.NameChanged(name)
                            )
                        },
                        onSurnameChanged = { surname ->
                            viewModel.onUiEvent(
                                loginUiEvent = LoginUiEvent.SurnameChanged(surname)
                            )
                        },
                        onPasswordChanged = { password ->
                            viewModel.onUiEvent(
                                loginUiEvent = LoginUiEvent.PasswordChanged(password)
                            )
                        },
                        onRePasswordChanged = { rePassword ->
                            viewModel.onUiEvent(
                                loginUiEvent = LoginUiEvent.RePasswordChanged(rePassword)
                            )
                        },
                        onRegister = {
                            viewModel.onUiEvent(loginUiEvent = LoginUiEvent.Register)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DemoScreenContent(
    state: LoginState,
    onMobileChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onSurnameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRePasswordChanged: (String) -> Unit,
    onRegister: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = state.name,
            onValueChange = onNameChanged,
            label = { Text(text = "Name") })
        TextField(
            value = state.surname,
            onValueChange = onSurnameChanged,
            label = { Text(text = "Surname") })
        TextField(
            value = state.mobile,
            onValueChange = onMobileChanged,
            label = { Text(text = "Mobile") })
        TextField(
            value = state.password,
            onValueChange = onPasswordChanged,
            label = { Text(text = "Password") })
        TextField(
            value = state.rePassword,
            onValueChange = onRePasswordChanged,
            label = { Text(text = "RePassword") })

        Button(onClick = onRegister) {
            Text(text = "Register")
        }
    }
}