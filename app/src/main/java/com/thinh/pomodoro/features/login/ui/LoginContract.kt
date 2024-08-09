package com.thinh.pomodoro.features.login.ui

import com.thinh.podomoro.mvi.BaseMviContract
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginEvent
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginUiState

interface LoginContract : BaseMviContract<LoginUiState, LoginEvent> {

    data class LoginUiState(
        val userName: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
    )

    sealed class LoginEvent {
        class OnUserNameChanged(val userName: String) : LoginEvent()
        class OnPasswordChanged(val password: String) : LoginEvent()
        object Login : LoginEvent()
        object OnForgotPassword : LoginEvent()
        object Register : LoginEvent()
    }

}