package com.thinh.pomodoro.features.registration.ui

import com.thinh.podomoro.mvi.BaseMviContract
import com.thinh.pomodoro.features.registration.ui.RegistrationContract.RegistrationEvent
import com.thinh.pomodoro.features.registration.ui.RegistrationContract.RegistrationUiState

interface RegistrationContract : BaseMviContract<RegistrationUiState, RegistrationEvent> {

    data class RegistrationUiState(
        val userName: String = "",
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val errorMessage: String? = null,
        val isLoading: Boolean = false,
        val isRegistered: Boolean = false
    )


    sealed class RegistrationEvent {
        data class OnUserNameChanged(val userName: String) : RegistrationEvent()
        data class OnEmailChanged(val email: String) : RegistrationEvent()
        data class OnPasswordChanged(val password: String) : RegistrationEvent()
        data class OnConfirmPasswordChanged(val confirmPassword: String) : RegistrationEvent()
        data object Register : RegistrationEvent()
        data object ShowedErrorMessage : RegistrationEvent()
    }
}