package com.thinh.pomodoro.features.login.ui

import com.thinh.pomodoro.features.login.ui.LoginContract.LoginEvent
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginUiState
import com.thinh.pomodoro.mvi.BaseViewModel

class LoginViewModel(
) : BaseViewModel<LoginUiState, LoginEvent>() {

    override fun createInitialState(): LoginUiState {
        return LoginUiState()
    }

    override fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                TODO("Not yet implemented")
            }

            is LoginEvent.OnPasswordChanged -> TODO()
            is LoginEvent.OnUserNameChanged -> TODO()
            LoginEvent.OnForgotPassword -> TODO()
            LoginEvent.Register -> TODO()
        }
    }


}