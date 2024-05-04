package com.thinh.podomoro.mvi

interface BaseUseCase<Input, Output> {
    suspend fun execute(input: Input): Output
}