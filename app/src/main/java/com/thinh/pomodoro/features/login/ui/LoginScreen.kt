package com.thinh.pomodoro.features.login.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thinh.pomodoro.R
import com.thinh.pomodoro.common.AppScaffold
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginEvent
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginEvent.Login
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginEvent.OnForgotPassword
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginEvent.OnPasswordChanged
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginEvent.OnUserNameChanged
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginEvent.ShowedErrorMessage
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginUiState
import com.thinh.pomodoro.ui.customcomposable.PasswordTextField
import com.thinh.pomodoro.ui.customcomposable.UserNameTextField

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
    navigateToRegisterScreen: () -> Unit,
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

    AppScaffold(
        title = "",
        isLoading = uiState.isLoading
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                UserNameTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    value = uiState.userName,
                    onValueChange = { newValue ->
                        onEvent(OnUserNameChanged(newValue))
                    },
                    label = stringResource(id = R.string.user_name),
                    isError = false,
                    errorText = "Something went wrong"
                )

                // Password
                PasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    value = uiState.password,
                    onValueChange = { newValue ->
                        onEvent(OnPasswordChanged(newValue))
                    },
                    label = stringResource(id = R.string.login_password_label),
                    isError = false,
                    errorText = "Something went wrong",
                    imeAction = ImeAction.Done
                )

                // Forgot Password
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(alignment = Alignment.End)
                        .clickable {
                            onEvent(OnForgotPassword)
                        },
                    text = stringResource(id = R.string.forgot_password),
//                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium
                )

                // Login Submit Button
                Button(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(vertical = 24.dp),
                    onClick = { onEvent(Login) }
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 32.dp),
                        text = stringResource(id = R.string.login_button_text),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                // Register Section
                Row(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Don't have an account?
                    Text(text = stringResource(id = R.string.do_not_have_account))

                    //Register
                    Text(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .clickable {
                                navigateToRegisterScreen()
                            },
                        text = stringResource(id = R.string.register),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

        }
    }
}

@Composable
fun NormalButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.wrapContentSize(),
        onClick = onClick
    ) {
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun ErrorTextInputField(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.error
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        uiState = LoginUiState(),
        onEvent = {},
        navigateToRegisterScreen = {}
    )
}