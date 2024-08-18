@file:OptIn(ExperimentalMaterial3Api::class)

package uz.mobile.footzone.android.presentation.screens.auth.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import uz.mobile.footzone.android.R
import uz.mobile.footzone.android.presentation.components.AppPrimaryButton
import uz.mobile.footzone.android.presentation.components.AppTopBar
import uz.mobile.footzone.android.presentation.formatter.PhoneNumberVisualTransformation
import uz.mobile.footzone.android.theme.MyApplicationTheme
import uz.mobile.footzone.android.theme.blue100
import uz.mobile.footzone.android.theme.blue600
import uz.mobile.footzone.android.theme.neutral10
import uz.mobile.footzone.android.theme.neutral100
import uz.mobile.footzone.android.theme.neutral40
import uz.mobile.footzone.android.theme.neutral80
import uz.mobile.footzone.android.theme.neutral90
import uz.mobile.footzone.android.theme.red600
import uz.mobile.footzone.domain.model.UserType
import uz.mobile.footzone.presentation.auth.register.RegisterState
import uz.mobile.footzone.presentation.auth.register.RegisterUiEvent
import uz.mobile.footzone.presentation.auth.register.mobileEmptyErrorState
import uz.mobile.footzone.presentation.auth.register.nameEmptyErrorState
import uz.mobile.footzone.presentation.auth.register.passwordEmptyErrorState
import uz.mobile.footzone.presentation.auth.register.passwordNotMatchErrorState
import uz.mobile.footzone.presentation.auth.register.surnameEmptyErrorState

@Composable
fun RegistrationScreen(modifier: Modifier = Modifier) {

    val viewModel: AuthViewModel = koinViewModel()
    val registerState by viewModel.state.collectAsState()

    if (registerState.isRegisterSuccessful) {
//        navigator.push(OTPValidationScreen(registerState.mobile))
    }

    RegistrationScreenContent(
        registerState = registerState,
        onUserTypeChange = {
            viewModel.onUiEvent(RegisterUiEvent.UserTypeChanged(it))
        },
        onNameChanged = {
            viewModel.onUiEvent(RegisterUiEvent.NameChanged(it))
        },
        onMobileChanged = {
            viewModel.onUiEvent(RegisterUiEvent.MobileChanged(it))
        },
        onPasswordChanged = {
            viewModel.onUiEvent(RegisterUiEvent.PasswordChanged(it))
        },
        onRePasswordChanged = {
            viewModel.onUiEvent(RegisterUiEvent.RePasswordChanged(it))
        },
        onSurnameChanged = {
            viewModel.onUiEvent(RegisterUiEvent.SurnameChanged(it))
        },
        onRegister = {
            viewModel.onUiEvent(RegisterUiEvent.Register)
        },
        navigateToLogin = {
//            navigator.pop()
        },
        onBackPressed = {
//            navigator.pop()
        }
    )
}

@Composable
fun RegistrationScreenContent(
    registerState: RegisterState,
    onUserTypeChange: (UserType) -> Unit,
    onNameChanged: (String) -> Unit,
    onMobileChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRePasswordChanged: (String) -> Unit,
    onSurnameChanged: (String) -> Unit,
    onRegister: () -> Unit,
    navigateToLogin: () -> Unit,
    onBackPressed: () -> Unit
) {

    val context = LocalContext.current

    if (registerState.isLoading) {
        Toast.makeText(context, stringResource(R.string.loading), Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(id = R.string.register),
                onBack = onBackPressed
            )
        },
        content = { paddingValues ->
            RegistrationScreenInput(
                modifier = Modifier.padding(paddingValues),
                state = registerState,
                onUserTypeChange = onUserTypeChange,
                onMobileChanged = onMobileChanged,
                onNameChanged = onNameChanged,
                onSurnameChanged = onSurnameChanged,
                onPasswordChanged = onPasswordChanged,
                onRePasswordChanged = onRePasswordChanged,
                onRegister = onRegister,
                navigateToLogin = navigateToLogin
            )
        }
    )

    LaunchedEffect(key1 = registerState.errorState.networkErrorState) {
        Toast.makeText(
            context,
            registerState.errorState.networkErrorState.errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
fun RegistrationScreenInput(
    modifier: Modifier = Modifier,
    state: RegisterState,
    onUserTypeChange: (UserType) -> Unit,
    onMobileChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onSurnameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRePasswordChanged: (String) -> Unit,
    onRegister: () -> Unit,
    navigateToLogin: () -> Unit,
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
            text = stringResource(id = R.string.register),
            onClick = onRegister,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 16.dp),
            enabled = state.registerEnabled
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = "Ro’yxatdan o’tish orqali siz bizning\n" +
                    "shartlarimizga va maxfiylik siyosatimizga\n" +
                    "rozilik bildirasiz",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center)
        )

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
                text = stringResource(R.string.have_account),
                style = MaterialTheme.typography.bodyMedium.copy(color = neutral90)
            )
            Text(
                text = stringResource(R.string.enter_acccount),
                modifier = Modifier.clickable(onClick = navigateToLogin),
                style = MaterialTheme.typography.bodyMedium.copy(color = blue600)
            )
        }
    }
}

@Composable
fun RegistrationInputs(
    modifier: Modifier = Modifier,
    state: RegisterState,
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
            label = stringResource(R.string.name),
            hasError = errorState == nameEmptyErrorState,
            errorMessage = errorState.errorMessage
        )
        RegisterInputField(
            value = state.surname,
            onValueChange = onSurnameChanged,
            label = stringResource(R.string.surname),
            hasError = errorState == surnameEmptyErrorState,
            errorMessage = errorState.errorMessage
        )
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
        RegisterInputField(
            value = state.rePassword,
            onValueChange = onRePasswordChanged,
            label = stringResource(R.string.repassword),
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
            .fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = neutral100)
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
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.bodySmall.copy(color = red600)
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedIndicatorColor = blue600,
                unfocusedIndicatorColor = neutral40,
                focusedLabelColor = blue600,
                unfocusedLabelColor = neutral80,
                errorTextColor = red600,
                focusedTextColor = neutral100,
            ),
            label = {
                Text(
                    text = label
                )
            },
        )
    }
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
        visualTransformation = PhoneNumberVisualTransformation(),
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = neutral100)
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
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.bodySmall.copy(color = red600)
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedIndicatorColor = blue600,
                unfocusedIndicatorColor = neutral40,
                errorTextColor = red600,
                focusedTextColor = neutral100,
                focusedLabelColor = blue600,
                unfocusedLabelColor = neutral80,
            ),
            label = {
                Text(
                    text = label
                )
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
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = neutral100),
            value = selectedValue.value,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            label = {
                Text(
                    text = stringResource(R.string.user_type)
                )
            },
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedIndicatorColor = blue600,
                unfocusedIndicatorColor = neutral40,
                focusedTextColor = neutral100,
                focusedLabelColor = blue600,
                unfocusedLabelColor = neutral80,
            ),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option: UserType ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option.value,
                            style = MaterialTheme.typography.bodyLarge.copy(color = neutral90)
                        )
                    },
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

val userTypes = listOf(UserType.USER, UserType.STADIUM_OWNER)

@Preview
@Composable
fun RegisterScreenContentPr(modifier: Modifier = Modifier) {
    MyApplicationTheme {

        RegistrationScreenContent(
            registerState = RegisterState(),
            onUserTypeChange = {},
            onNameChanged = {},
            onMobileChanged = {},
            onPasswordChanged = {},
            onRePasswordChanged = {},
            onSurnameChanged = {},
            onRegister = { },
            {}) {

        }
    }
}