package com.thinh.pomodoro.features.pomodoro.data

data class WorkDay(
    val id: Int = 0,
    val date: Long,
    val typeOfPomodoro: Int,
    val startTime: Long,
    val endTime: Long
)