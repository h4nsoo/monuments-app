package com.example.mohamedbelgacem.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Quiz ViewModel
 *
 * Manages the state and business logic for the quiz screens.
 *
 * Responsibilities (to be implemented):
 * - Quiz question management
 * - User answer tracking
 * - Score calculation
 * - Progress tracking
 * - Timer management
 * - Difficulty level selection
 * - Category selection
 *
 * Uses StateFlow for reactive state management in Compose.
 */
class QuizViewModel : ViewModel() {

    // State: Current quiz state (to be defined with proper data class)
    private val _uiState = MutableStateFlow<QuizUiState>(QuizUiState.Idle)
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    // State: Current score (temporary placeholder)
    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    // State: Current question index (temporary placeholder)
    private val _questionIndex = MutableStateFlow(0)
    val questionIndex: StateFlow<Int> = _questionIndex.asStateFlow()

    /**
     * Initializes a quiz session
     * @param category The quiz category
     * @param difficulty The difficulty level (Easy, Medium, Hard)
     */
    fun initializeQuiz(category: String, difficulty: String) {
        // TODO: Load questions based on category and difficulty
        // TODO: Initialize state
    }

    /**
     * Submits an answer for the current question
     * @param answer The selected answer
     */
    fun submitAnswer(answer: String) {
        // TODO: Validate answer
        // TODO: Update score
        // TODO: Move to next question
    }

    /**
     * Moves to the next question
     */
    fun nextQuestion() {
        // TODO: Update question index
        // TODO: Check if quiz is complete
    }

    /**
     * Moves to the previous question
     */
    fun previousQuestion() {
        // TODO: Update question index
    }

    /**
     * Completes the quiz and navigates to results
     */
    fun finishQuiz() {
        // TODO: Calculate final score and statistics
        // TODO: Save results
        // TODO: Update state to Results
    }

    /**
     * Resets the quiz state
     */
    fun resetQuiz() {
        _uiState.value = QuizUiState.Idle
        _score.value = 0
        _questionIndex.value = 0
    }
}

/**
 * Quiz UI State
 *
 * Represents different states of the quiz experience
 */
sealed class QuizUiState {
    data object Idle : QuizUiState()
    data object Loading : QuizUiState()
    data object InProgress : QuizUiState()
    data object Completed : QuizUiState()
    data class Error(val message: String) : QuizUiState()
}
