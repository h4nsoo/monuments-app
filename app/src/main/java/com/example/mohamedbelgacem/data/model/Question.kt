package com.example.mohamedbelgacem.data.model

data class Question(
    val id: Int,
    val image: String,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int,
    val category: Category,
    val difficulty: Difficulty
)
