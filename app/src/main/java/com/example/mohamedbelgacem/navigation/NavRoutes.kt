package com.example.mohamedbelgacem.navigation

object NavRoutes {
    const val SPLASH = "splash"
    const val MENU = "menu"
    const val CATEGORIES = "categories"
    const val SETTINGS = "settings"
    const val QUIZ = "quiz/{category}/{difficulty}"
    const val RESULTS = "results"

    /**
     * Generate quiz route with parameters
     * @param category Quiz category
     * @param difficulty Difficulty level
     */
    fun quizRoute(category: String, difficulty: String): String {
        return "quiz/$category/$difficulty"
    }
}
