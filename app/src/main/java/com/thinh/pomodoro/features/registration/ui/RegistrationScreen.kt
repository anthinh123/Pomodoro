@file:JvmName("RegistrationScreenKt")

package com.thinh.pomodoro.features.registration.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thinh.pomodoro.R
import com.thinh.pomodoro.common.AppScaffold
import com.thinh.pomodoro.features.login.ui.LoginContract
import com.thinh.pomodoro.features.registration.ui.RegistrationContract.RegistrationEvent
import com.thinh.pomodoro.features.registration.ui.RegistrationContract.RegistrationEvent.ShowedErrorMessage
import com.thinh.pomodoro.features.registration.ui.RegistrationContract.RegistrationUiState
import com.thinh.pomodoro.ui.customcomposable.EmailTextField
import com.thinh.pomodoro.ui.customcomposable.PasswordTextField
import com.thinh.pomodoro.ui.customcomposable.UserNameTextField

@Composable
fun RegistrationScreen(
    uiState: RegistrationUiState,
    onBack: () -> Unit,
    onEvent: (RegistrationEvent) -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage?.isNotBlank() == true) {
            Toast.makeText(
                context,
                uiState.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            onEvent(ShowedErrorMessage)
        }
    }

    LaunchedEffect(uiState.isRegistered) {
        if (uiState.isRegistered) {
            onBack.invoke()
        }
    }

    AppScaffold(
        title = stringResource(id = R.string.registration_heading_text),
        isCanBack = true,
        onBackClicked = { onBack.invoke() },
        isLoading = uiState.isLoading
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            UserNameTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                value = uiState.userName,
                onValueChange = { newValue ->
                    onEvent(RegistrationEvent.OnUserNameChanged(newValue))
                },
                label = stringResource(id = R.string.user_name),
                isError = false,
                errorText = "Something went wrong",
                imeAction = ImeAction.Next
            )

            EmailTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                value = uiState.email,
                onValueChange = { newValue ->
                    onEvent(RegistrationEvent.OnEmailChanged(newValue))
                },
                label = stringResource(id = R.string.email),
                isError = false,
                errorText = "Something went wrong",
                imeAction = ImeAction.Next
            )

            // Password
            PasswordTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                value = uiState.password,
                onValueChange = { newValue ->
                    onEvent(RegistrationEvent.OnPasswordChanged(newValue))
                },
                label = stringResource(id = R.string.login_password_label),
                isError = false,
                errorText = "Something went wrong",
                imeAction = ImeAction.Next
            )

            PasswordTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                value = uiState.confirmPassword,
                onValueChange = { newValue ->
                    onEvent(RegistrationEvent.OnConfirmPasswordChanged(newValue))
                },
                label = stringResource(id = R.string.confirm_password_label),
                isError = false,
                errorText = "Something went wrong",
                imeAction = ImeAction.Done
            )

            // Registration Submit Button
            Button(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 24.dp),
                onClick = {
                    onEvent(RegistrationEvent.Register)
                }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 32.dp),
                    text = stringResource(id = R.string.register_button_text),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen(
        uiState = RegistrationUiState(),
        onBack = {},
        onEvent = {}
    )
}