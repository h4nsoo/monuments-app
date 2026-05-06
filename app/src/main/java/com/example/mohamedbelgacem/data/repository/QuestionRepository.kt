package com.example.mohamedbelgacem.data.repository

import com.example.mohamedbelgacem.data.loader.QuestionLoader
import com.example.mohamedbelgacem.data.model.Category
import com.example.mohamedbelgacem.data.model.Difficulty
import com.example.mohamedbelgacem.data.model.Question

class QuestionRepository(private val loader: QuestionLoader) {
    private val questions: List<Question> by lazy { loader.loadQuestions() }

    fun getAllQuestions(): List<Question> = questions

    fun getQuestionsByCategoryAndDifficulty(
        category: Category,
        difficulty: Difficulty
    ): List<Question> {
        return questions.filter { it.category == category && it.difficulty == difficulty }
    }
}
