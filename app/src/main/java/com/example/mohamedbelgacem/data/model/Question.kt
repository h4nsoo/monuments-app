package com.example.mohamedbelgacem.data.model

/**
 * Question Data Model
 *
 * Represents a single quiz question.
 * To be populated with actual quiz data.
 *
 * @param id Unique question identifier
 * @param category Category of the question
 * @param difficulty Difficulty level (Easy, Medium, Hard)
 * @param text The question text
 * @param options List of answer options
 * @param correctAnswer The correct answer
 * @param explanation Explanation for the correct answer
 */
data class Question(
    val id: String,
    val category: String,
    val difficulty: String,
    val text: String,
    val options: List<String>,
    val correctAnswer: String,
    val explanation: String = ""
)
