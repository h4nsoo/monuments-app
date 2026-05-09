package com.example.mohamedbelgacem.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mohamedbelgacem.data.loader.QuestionLoader
import com.example.mohamedbelgacem.data.model.Category
import com.example.mohamedbelgacem.data.model.Difficulty
import com.example.mohamedbelgacem.data.model.Question
import com.example.mohamedbelgacem.data.repository.QuestionRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = QuestionRepository(QuestionLoader(application))

    // --- Settings ---
    private val _difficulty = MutableStateFlow(Difficulty.MEDIUM)
    val difficulty: StateFlow<Difficulty> = _difficulty.asStateFlow()

    private val _timerEnabled = MutableStateFlow(true)
    val timerEnabled: StateFlow<Boolean> = _timerEnabled.asStateFlow()

    private val _audioEnabled = MutableStateFlow(false)
    val audioEnabled: StateFlow<Boolean> = _audioEnabled.asStateFlow()

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    // --- Quiz State ---
    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    private val _userAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val userAnswers: StateFlow<Map<Int, Int>> = _userAnswers.asStateFlow()

    private val _answerRevealed = MutableStateFlow(false)
    val answerRevealed: StateFlow<Boolean> = _answerRevealed.asStateFlow()

    private val _quizUiState = MutableStateFlow<QuizUiState>(QuizUiState.Idle)
    val quizUiState: StateFlow<QuizUiState> = _quizUiState.asStateFlow()

    private val _timeRemaining = MutableStateFlow(15)
    val timeRemaining: StateFlow<Int> = _timeRemaining.asStateFlow()

    private val _totalElapsedSeconds = MutableStateFlow(0L)
    val totalElapsedSeconds: StateFlow<Long> = _totalElapsedSeconds.asStateFlow()

    // --- Score & Feedback State ---
    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    private val _showingFeedback = MutableStateFlow(false)
    val showingFeedback: StateFlow<Boolean> = _showingFeedback.asStateFlow()

    private val _lastAnswerCorrect = MutableStateFlow(false)
    val lastAnswerCorrect: StateFlow<Boolean> = _lastAnswerCorrect.asStateFlow()

    private val _feedbackCorrectText = MutableStateFlow("")
    val feedbackCorrectText: StateFlow<String> = _feedbackCorrectText.asStateFlow()

    private val _feedbackAnswerGiven = MutableStateFlow(false)
    val feedbackAnswerGiven: StateFlow<Boolean> = _feedbackAnswerGiven.asStateFlow()

    private var questionTimerJob: Job? = null
    private var elapsedTimerJob: Job? = null

    // --- Settings Setters ---

    fun selectCategory(category: Category) {
        _selectedCategory.value = category
    }

    fun setDifficulty(d: Difficulty) {
        _difficulty.value = d
    }

    fun setTimerEnabled(enabled: Boolean) {
        _timerEnabled.value = enabled
    }

    fun setAudioEnabled(enabled: Boolean) {
        _audioEnabled.value = enabled
    }

    // --- Quiz Logic ---

    fun initializeQuiz() {
        val diff = _difficulty.value
        val cat = _selectedCategory.value

        val qs: List<Question> = when {
            cat != null -> {
                repository.getQuestionsByCategoryAndDifficulty(cat, diff)
                    .ifEmpty { repository.getAllQuestions().filter { it.difficulty == diff } }
                    .ifEmpty { repository.getAllQuestions() }
            }
            else -> {
                repository.getAllQuestions().filter { it.difficulty == diff }
                    .ifEmpty { repository.getAllQuestions() }
            }
        }

        _questions.value = qs.shuffled().take(12)
        _currentIndex.value = 0
        _userAnswers.value = emptyMap()
        _answerRevealed.value = false
        _totalElapsedSeconds.value = 0L
        _score.value = 0
        _showingFeedback.value = false
        _lastAnswerCorrect.value = false
        _feedbackCorrectText.value = ""
        _feedbackAnswerGiven.value = false
        _quizUiState.value = QuizUiState.InProgress

        startElapsedTimer()
        if (_timerEnabled.value) startQuestionTimer()
    }

    fun selectAnswer(answerIndex: Int) {
        if (_answerRevealed.value) return
        val idx = _currentIndex.value
        _userAnswers.value = _userAnswers.value + (idx to answerIndex)
        _answerRevealed.value = true
        questionTimerJob?.cancel()

        val question = _questions.value.getOrNull(idx)
        val isCorrect = question?.correctAnswer == answerIndex
        if (isCorrect) _score.value += 10
        _lastAnswerCorrect.value = isCorrect
        _feedbackCorrectText.value = question?.options?.getOrNull(question.correctAnswer) ?: ""
        _feedbackAnswerGiven.value = true
        _showingFeedback.value = true
    }

    fun dismissFeedback() {
        _showingFeedback.value = false
        advanceOrFinish()
    }

    private fun advanceOrFinish() {
        val next = _currentIndex.value + 1
        if (next >= _questions.value.size) {
            elapsedTimerJob?.cancel()
            _quizUiState.value = QuizUiState.Completed
        } else {
            _currentIndex.value = next
            _answerRevealed.value = false
            if (_timerEnabled.value) startQuestionTimer()
        }
    }

    private fun startQuestionTimer() {
        questionTimerJob?.cancel()
        _timeRemaining.value = 15
        questionTimerJob = viewModelScope.launch {
            while (_timeRemaining.value > 0) {
                delay(1000)
                _timeRemaining.value = (_timeRemaining.value - 1).coerceAtLeast(0)
            }
            if (!_answerRevealed.value) {
                _answerRevealed.value = true
                val question = _questions.value.getOrNull(_currentIndex.value)
                _lastAnswerCorrect.value = false
                _feedbackCorrectText.value = question?.options?.getOrNull(question.correctAnswer) ?: ""
                _feedbackAnswerGiven.value = false
                _showingFeedback.value = true
            }
        }
    }

    private fun startElapsedTimer() {
        elapsedTimerJob?.cancel()
        elapsedTimerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _totalElapsedSeconds.value += 1
            }
        }
    }

    // --- Result Helpers ---

    fun getScore(): Int {
        val qs = _questions.value
        return _userAnswers.value.count { (index, answer) ->
            qs.getOrNull(index)?.correctAnswer == answer
        }
    }

    fun getIncorrectCount() = _userAnswers.value.size - getScore()

    fun resetQuiz() {
        questionTimerJob?.cancel()
        elapsedTimerJob?.cancel()
        _quizUiState.value = QuizUiState.Idle
        _currentIndex.value = 0
        _userAnswers.value = emptyMap()
        _answerRevealed.value = false
        _totalElapsedSeconds.value = 0L
        _questions.value = emptyList()
        _score.value = 0
        _showingFeedback.value = false
        _lastAnswerCorrect.value = false
        _feedbackCorrectText.value = ""
        _feedbackAnswerGiven.value = false
    }
}

sealed class QuizUiState {
    data object Idle : QuizUiState()
    data object Loading : QuizUiState()
    data object InProgress : QuizUiState()
    data object Completed : QuizUiState()
    data class Error(val message: String) : QuizUiState()
}
