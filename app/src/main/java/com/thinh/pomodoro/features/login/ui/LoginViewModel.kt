package com.thinh.pomodoro.features.login.ui

import androidx.lifecycle.viewModelScope
import com.mtcld.repaircheck.core.retrofit.NetworkResult
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginEvent
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginEvent.Login
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginEvent.OnForgotPassword
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginEvent.OnPasswordChanged
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginEvent.OnUserNameChanged
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginEvent.Register
import com.thinh.pomodoro.features.login.ui.LoginContract.LoginUiState
import com.thinh.pomodoro.features.login.usecase.LoginUseCase
import com.thinh.pomodoro.mvi.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginUiState, LoginEvent>() {

    override fun createInitialState(): LoginUiState {
        return LoginUiState()
    }

    override fun handleEvent(event: LoginEvent) {
        when (event) {
            is Login -> login()
            is OnPasswordChanged -> updatePassword(event.password)
            is OnUserNameChanged -> updateUserName(event.userName)
            OnForgotPassword -> forgotPassword()
            Register -> register()
        }
    }

    private fun login() {
        if (!validateInput()) {
            updateState {
                copy(errorMessage = "UserName or password is incorrect")
            }
            return
        }

        updateState {
            copy(isLoading = true)
        }

        viewModelScope.launch {
            val result = loginUseCase.execute(
                username = uiState.value.userName,
                password = uiState.value.password
            )
            when (result) {
                is NetworkResult.Success -> {
                    updateState {
                        copy(isLoading = false, isLoggedIn = true)
                    }
                }

                is NetworkResult.Error -> {
                    updateState {
                        copy(isLoading = false, errorMessage = result.errorMsg)
                    }
                }

                is NetworkResult.Exception -> {
                    updateState {
                        copy(isLoading = false, errorMessage = "Something went wrong")
                    }
                }
            }
        }

    }

    private fun updateUserName(userName: String) {
        updateState {
            copy(userName = userName)
        }
    }

    private fun updatePassword(pwd: String) {
        updateState {
            copy(password = pwd)
        }
    }

    private fun forgotPassword() {

    }

    private fun register() {

    }

    private fun validateInput(): Boolean {
        return !(uiState.value.userName.isEmpty() || uiState.value.password.isEmpty())
    }

}