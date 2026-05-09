package com.example.mohamedbelgacem.data.repository

import com.example.mohamedbelgacem.data.loader.QuestionSource
import com.example.mohamedbelgacem.data.model.Category
import com.example.mohamedbelgacem.data.model.Difficulty
import com.example.mohamedbelgacem.data.model.Question

class QuestionRepository(private val source: QuestionSource) {
    private val questions: List<Question> by lazy { source.loadQuestions() }

    fun getAllQuestions(): List<Question> = questions

    fun getQuestionsByCategoryAndDifficulty(
        category: Category,
        difficulty: Difficulty
    ): List<Question> {
        return questions.filter { it.category == category && it.difficulty == difficulty }
    }
}
