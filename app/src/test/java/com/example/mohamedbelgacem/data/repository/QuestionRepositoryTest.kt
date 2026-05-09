package com.example.mohamedbelgacem.data.repository

import com.example.mohamedbelgacem.data.loader.QuestionSource
import com.example.mohamedbelgacem.data.model.Category
import com.example.mohamedbelgacem.data.model.Difficulty
import com.example.mohamedbelgacem.data.model.Question
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class QuestionRepositoryTest {

    private val questions = listOf(
        Question(
            id = 1,
            image = "eljem",
            question = "Which heritage site is shown?",
            options = listOf("El Jem", "Dougga", "Kerkouane", "Bulla Regia"),
            correctAnswer = 0,
            category = Category.ROMAN,
            difficulty = Difficulty.EASY,
        ),
        Question(
            id = 2,
            image = "dougga",
            question = "Which site belongs to Roman Tunisia?",
            options = listOf("Dougga", "Sousse", "Matmata", "Djerba"),
            correctAnswer = 0,
            category = Category.ROMAN,
            difficulty = Difficulty.HARD,
        ),
        Question(
            id = 3,
            image = "kairouan",
            question = "Which city is linked to Islamic heritage?",
            options = listOf("Kairouan", "Tunis", "Sfax", "Bizerte"),
            correctAnswer = 0,
            category = Category.ISLAMIC,
            difficulty = Difficulty.EASY,
        ),
    )

    @Test
    fun getAllQuestions_returnsAllQuestionsFromSource() {
        val repository = QuestionRepository(FakeQuestionSource(questions))

        val result = repository.getAllQuestions()

        assertEquals(questions, result)
    }

    @Test
    fun getQuestionsByCategoryAndDifficulty_filtersPrecisely() {
        val repository = QuestionRepository(FakeQuestionSource(questions))

        val result = repository.getQuestionsByCategoryAndDifficulty(
            category = Category.ROMAN,
            difficulty = Difficulty.EASY,
        )

        assertEquals(1, result.size)
        assertEquals(1, result.first().id)
        assertTrue(result.all { it.category == Category.ROMAN && it.difficulty == Difficulty.EASY })
    }

    private class FakeQuestionSource(
        private val sourceQuestions: List<Question>,
    ) : QuestionSource {
        override fun loadQuestions(assetFileName: String): List<Question> = sourceQuestions
    }
}
