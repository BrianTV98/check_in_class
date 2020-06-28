package com.example.check_in.data.model

data class Schedule (
    val dayOfWeek: DayOfWeek,
   val morning : SessionSchdule?,
    val affternoon : SessionSchdule?
)

data class  SessionSchdule(
    val subject : String,
    val number_classroom: String,
    val nameOfClass  : String,
    val timeBegin : String,
    val timeEnd : String
)