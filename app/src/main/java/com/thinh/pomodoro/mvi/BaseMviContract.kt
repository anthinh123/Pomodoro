package com.thinh.podomoro.mvi

import kotlinx.coroutines.flow.StateFlow

interface BaseMviContract<STATE, EVENT> {
    val uiState: StateFlow<STATE>
    fun event(event: EVENT)
}