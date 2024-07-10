@file:OptIn(ExperimentalMaterial3Api::class)

package uz.mobile.footzone.android.presentation.screens.auth

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.androidx.compose.koinViewModel
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.presentation.components.AppPrimaryButton
import uz.mobile.footzone.android.presentation.components.VerticalSpacer
import uz.mobile.footzone.android.presentation.formatter.PhoneNumberVisualTransformation
import uz.mobile.footzone.android.presentation.screens.main.MainScreen
import uz.mobile.footzone.android.theme.blue100
import uz.mobile.footzone.android.theme.blue600
import uz.mobile.footzone.android.theme.neutral10
import uz.mobile.footzone.android.theme.neutral40
import uz.mobile.footzone.model.UserType
import uz.mobile.footzone.viewmodel.login.AuthViewModel
import uz.mobile.footzone.viewmodel.login.LoginState
import uz.mobile.footzone.viewmodel.login.LoginUiEvent
import uz.mobile.footzone.viewmodel.login.mobileEmptyErrorState
import uz.mobile.footzone.viewmodel.login.nameEmptyErrorState
import uz.mobile.footzone.viewmodel.login.passwordEmptyErrorState
import uz.mobile.footzone.viewmodel.login.passwordNotMatchErrorState
import uz.mobile.footzone.viewmodel.login.surnameEmptyErrorState

class RegistrationScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        RegistrationScreenContent(
            viewModel = koinViewModel(),
            onNavigateToMainRoute = {
                navigator.push(MainScreen())
            }
        )
    }
}

@Composable
fun RegistrationScreenContent(
    viewModel: AuthViewModel,
    onNavigateToMainRoute: () -> Unit,
) {

    val uiState by viewModel.state.collectAsState()
    val context = LocalContext.current

    when {
        uiState.isLoading -> {
            Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
        }

        uiState.isLoginSuccessful -> {
            onNavigateToMainRoute()
        }
    }

    Scaffold(
        topBar = {
            RegistrationTopBar(
                onBack = {

                }
            )
        },
        content = { paddingValues ->
            RegistrationScreenInput(
                modifier = Modifier.padding(paddingValues),
                state = uiState,
                onUserTypeChange = { userType ->
                    viewModel.onUiEvent(
                        loginUiEvent = LoginUiEvent.UserTypeChanged(userType)
                    )
                },
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
    )

    LaunchedEffect(key1 = uiState.errorState.networkErrorState) {
        Toast.makeText(
            context,
            uiState.errorState.networkErrorState.errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
fun RegistrationScreenInput(
    modifier: Modifier = Modifier,
    state: LoginState,
    onUserTypeChange: (UserType) -> Unit,
    onMobileChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onSurnameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRePasswordChanged: (String) -> Unit,
    onRegister: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        RegistrationInputs(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            state = state,
            onUserTypeChange = onUserTypeChange,
            onMobileChanged = onMobileChanged,
            onNameChanged = onNameChanged,
            onSurnameChanged = onSurnameChanged,
            onPasswordChanged = onPasswordChanged,
            onRePasswordChanged = onRePasswordChanged
        )

        AppPrimaryButton(
            text = "Ro'yxatdan o'tish",
            onClick = onRegister,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 16.dp),
            enabled = state.registerEnabled
        )

        VerticalSpacer(Modifier.weight(1f))

        Text(
            text = "Ro’yxatdan o’tish orqali siz bizning\n" +
                    "shartlarimizga va maxfiylik siyosatimizga\n" +
                    "rozilik bildirasiz",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        VerticalSpacer(Modifier.weight(1f))

        HorizontalDivider(color = neutral40, modifier = Modifier.fillMaxWidth())
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 22.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Akkauntingiz bormi?")
            Text(text = "Kiring")
        }
    }
}

@Composable
fun RegistrationInputs(
    modifier: Modifier = Modifier,
    state: LoginState,
    onUserTypeChange: (UserType) -> Unit,
    onMobileChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onSurnameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRePasswordChanged: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        val errorState = state.errorState.networkErrorState

        DynamicSelectTextField(
            selectedValue = state.userType,
            options = userTypes,
            onValueChangedEvent = onUserTypeChange,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        RegisterInputField(
            value = state.name,
            onValueChange = onNameChanged,
            label = "Name",
            hasError = errorState == nameEmptyErrorState,
            errorMessage = errorState.errorMessage
        )
        RegisterInputField(
            value = state.surname,
            onValueChange = onSurnameChanged,
            label = "Surname",
            hasError = errorState == surnameEmptyErrorState,
            errorMessage = errorState.errorMessage
        )
        PhoneInput(
            value = state.mobile,
            onValueChange = onMobileChanged,
            label = "Mobile",
            hasError = errorState == mobileEmptyErrorState,
            errorMessage = errorState.errorMessage,
        )
        RegisterInputField(
            value = state.password,
            onValueChange = onPasswordChanged,
            label = "Password",
            hasError = errorState == passwordEmptyErrorState,
            errorMessage = errorState.errorMessage
        )
        RegisterInputField(
            value = state.rePassword,
            onValueChange = onRePasswordChanged,
            label = "RePassword",
            hasError = errorState == passwordNotMatchErrorState,
            errorMessage = errorState.errorMessage
        )
    }
}

@Composable
fun RegisterInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    hasError: Boolean,
    errorMessage: String,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val enabled = true

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextFieldDefaults.DecorationBox(
            value = value,
            visualTransformation = VisualTransformation.None,
            innerTextField = it,
            singleLine = true,
            enabled = enabled,
            interactionSource = interactionSource,
            // keep vertical paddings but change the horizontal
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                start = 16.dp, end = 19.dp
            ),
            isError = hasError,
            supportingText = {
                if (hasError) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.error),
                            contentDescription = ""
                        )
                        Text(text = errorMessage)
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedIndicatorColor = blue600,
                unfocusedIndicatorColor = neutral40,
            ),
            label = {
                Text(text = label)
            },
        )
    }

//    OutlinedTextField(
//        modifier = modifier
//            .fillMaxWidth(),
//        value = value,
//        onValueChange = onValueChange,
//        label = {
//            Text(text = label)
//        },
//        isError = hasError,
//        supportingText = {
//            if (hasError) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.spacedBy(6.dp)
//                ) {
//                    Icon(
//                        imageVector = ImageVector.vectorResource(id = R.drawable.error),
//                        contentDescription = ""
//                    )
//                    Text(text = errorMessage)
//                }
//            }
//        },
//        colors = OutlinedTextFieldDefaults.colors().copy(
//            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
//        ),
//        shape = MaterialTheme.shapes.small
//    )
}

@Composable
fun PhoneInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    hasError: Boolean,
    errorMessage: String,
    keyboardType: KeyboardType = KeyboardType.Phone
) {

    val interactionSource = remember { MutableInteractionSource() }
    val enabled = true

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        modifier = modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = PhoneNumberVisualTransformation()
    ) {
        OutlinedTextFieldDefaults.DecorationBox(
            value = value,
            visualTransformation = VisualTransformation.None,
            innerTextField = it,
            singleLine = true,
            enabled = enabled,
            interactionSource = interactionSource,
            // keep vertical paddings but change the horizontal
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                start = 16.dp, end = 19.dp
            ),
            isError = hasError,
            supportingText = {
                if (hasError) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.error),
                            contentDescription = ""
                        )
                        Text(text = errorMessage)
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedIndicatorColor = blue600,
                unfocusedIndicatorColor = neutral40,
            ),
            label = {
                Text(text = label)
            },
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    selectedValue: UserType,
    options: List<UserType>,
    onValueChangedEvent: (UserType) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue.value,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            label = {
                Text(text = "Foydalanuvchi turi")
            },
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedIndicatorColor = blue600,
                unfocusedIndicatorColor = neutral40,
            ),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option: UserType ->
                DropdownMenuItem(
                    text = { Text(text = option.value) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(option)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (option == selectedValue) blue100 else neutral10
                        )
                )
            }
        }
    }
}

@Composable
fun RegistrationTopBar(modifier: Modifier = Modifier, onBack: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = {
            Text("Ro'yxatdan o'tish")
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}

val userTypes = listOf(UserType.USER, UserType.STADIUM_OWNER)