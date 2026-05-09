package com.example.mohamedbelgacem.data.loader

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mohamedbelgacem.data.model.Category
import com.example.mohamedbelgacem.data.model.Difficulty
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuestionLoaderInstrumentedTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun loadQuestions_readsQuestionsFromAssets() {
        val loader = QuestionLoader(context)

        val questions = loader.loadQuestions()

        assertEquals(15, questions.size)
        assertTrue(questions.any { it.image == "tunis" })
        assertTrue(questions.all { it.category == Category.CITIES })
        assertTrue(questions.any { it.difficulty == Difficulty.EASY })
    }
}
