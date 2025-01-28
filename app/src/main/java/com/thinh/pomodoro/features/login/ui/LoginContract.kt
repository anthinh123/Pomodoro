package com.thinh.pomodoro.features.login.ui

import com.thinh.podomoro.mvi.BaseMviContract
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginEvent
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginUiState

interface LoginContract : BaseMviContract<LoginUiState, LoginEvent> {

    data class LoginUiState(
        val userName: String = "",
        val password: String = "",
        val errorMessage: String? = null,
        val isLoggedIn: Boolean = false,
        val isLoading: Boolean = false,
    )

    sealed class LoginEvent {
        class OnUserNameChanged(val userName: String) : LoginEvent()
        class OnPasswordChanged(val password: String) : LoginEvent()
        data object Login : LoginEvent()
        data object OnForgotPassword : LoginEvent()
        data object Register : LoginEvent()
        data object ShowedErrorMessage : LoginEvent()
    }

}