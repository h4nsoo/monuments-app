package com.example.mohamedbelgacem.data.loader

import android.content.Context
import com.example.mohamedbelgacem.data.model.Question
import com.google.gson.Gson
import java.io.IOException

interface QuestionSource {
    fun loadQuestions(assetFileName: String = "roman_questions.json"): List<Question>
}

class QuestionLoader(private val context: Context) : QuestionSource {
    private val gson = Gson()

    override fun loadQuestions(assetFileName: String): List<Question> {
        return try {
            context.assets.open(assetFileName).bufferedReader().use { reader ->
                gson.fromJson(reader, Array<Question>::class.java)?.toList().orEmpty()
            }
        } catch (_: IOException) {
            emptyList()
        } catch (_: Exception) {
            emptyList()
        }
    }
}

fun getImageResId(context: Context, imageName: String): Int {
    return context.resources.getIdentifier(imageName, "drawable", context.packageName)
}
