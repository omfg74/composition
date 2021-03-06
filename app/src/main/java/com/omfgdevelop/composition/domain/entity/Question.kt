package com.omfgdevelop.composition.domain.entity

data class Question(
    val sum: Int,
    val visibleNumber: Int,
    val options: List<Int>,
    val correctAnswer: Int = sum - visibleNumber
)
