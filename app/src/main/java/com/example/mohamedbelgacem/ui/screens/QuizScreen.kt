package com.example.mohamedbelgacem.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

/**
 * Quiz Screen
 *
 * Main quiz interface displaying:
 * - Current question and options
 * - Progress indicator
 * - Timer (to be implemented)
 * - Navigation to next/previous questions
 * - Skip functionality
 *
 * Connected to QuizViewModel for state management.
 * @param category The quiz category (passed via navigation route)
 * @param difficulty The difficulty level (passed via navigation route)
 */
@Composable
fun QuizScreen(
    category: String = "History",
    difficulty: String = "Medium"
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Quiz: $category - $difficulty",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
    }
}
