package com.example.mohamedbelgacem.data.model

/**
 * Quiz Result Data Model
 *
 * Represents the result of a completed quiz session.
 *
 * @param id Unique result identifier
 * @param category Category of the quiz
 * @param difficulty Difficulty level
 * @param totalQuestions Total questions attempted
 * @param correctAnswers Number of correct answers
 * @param score Final score percentage
 * @param timeTaken Time taken to complete quiz (in seconds)
 * @param timestamp When the quiz was completed
 * @param answers List of answers provided by user
 */
data class QuizResult(
    val id: String,
    val category: String,
    val difficulty: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val score: Int,
    val timeTaken: Long,
    val timestamp: Long,
    val answers: List<String> = emptyList()
)
