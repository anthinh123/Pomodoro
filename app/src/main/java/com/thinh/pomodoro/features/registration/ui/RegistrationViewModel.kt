package com.thinh.pomodoro.features.registration.ui

import androidx.lifecycle.viewModelScope
import com.thinh.pomodoro.retrofit.NetworkResult
import com.thinh.pomodoro.features.registration.ui.RegistrationContract.RegistrationEvent
import com.thinh.pomodoro.features.registration.ui.RegistrationContract.RegistrationUiState
import com.thinh.pomodoro.features.registration.usecase.RegistrationUseCase
import com.thinh.pomodoro.mvi.BaseViewModel
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val registrationUseCase: RegistrationUseCase,
) : BaseViewModel<RegistrationUiState, RegistrationEvent>() {
    override fun createInitialState(): RegistrationUiState {
        return RegistrationUiState()
    }

    override fun handleEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnConfirmPasswordChanged -> onConfirmPasswordChanged(event.confirmPassword)
            is RegistrationEvent.OnEmailChanged -> onEmailChanged(event.email)
            is RegistrationEvent.OnPasswordChanged -> onPasswordChanged(event.password)
            is RegistrationEvent.OnUserNameChanged -> onUserNameChanged(event.userName)
            RegistrationEvent.Register -> register()
            RegistrationEvent.ShowedErrorMessage -> resetErrorMessage()
        }
    }

    private fun onUserNameChanged(userName: String) {
        updateState {
            copy(userName = userName)
        }
    }

    private fun onEmailChanged(email: String) {
        updateState {
            copy(email = email)
        }
    }

    private fun onPasswordChanged(password: String) {
        updateState {
            copy(password = password)
        }
    }

    private fun onConfirmPasswordChanged(confirmPassword: String) {
        updateState {
            copy(confirmPassword = confirmPassword)
        }
    }

    private fun register() {

        if (!checkRequiredFields()) {
            updateState {
                copy(errorMessage = "Need to fill all fields")
            }
            return
        }

        if (!validateConfirmPassword()) {
            updateState {
                copy(errorMessage = "Need to match password")
            }
            return
        }


        viewModelScope.launch {
            updateState {
                copy(isLoading = true)
            }

            val result = registrationUseCase.execute(
                userName = uiState.value.userName,
                email = uiState.value.email,
                password = uiState.value.password
            )

            when (result) {
                is NetworkResult.Success -> {
                    updateState {
                        copy(isLoading = false, isRegistered = true)
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

    private fun validateConfirmPassword(): Boolean {
        return uiState.value.password == uiState.value.confirmPassword
    }

    private fun checkRequiredFields(): Boolean {
        return !(uiState.value.userName.isBlank() || uiState.value.password.isBlank() ||
                uiState.value.confirmPassword.isBlank() || uiState.value.email.isBlank())
    }

    private fun resetErrorMessage() {
        updateState {
            copy(errorMessage = null)
        }
    }

}