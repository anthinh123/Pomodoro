package com.thinh.pomodoro.utils

object TimeConvertor {
    fun convertMillisToTime(totalSeconds: Long): String {
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "${minutes.toString().padStart(2, '0')} : ${seconds.toString().padStart(2, '0')}"
    }
}