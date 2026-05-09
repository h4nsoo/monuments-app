package com.example.mohamedbelgacem.navigation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NavRoutesTest {

    @Test
    fun routes_areStableAndUnique() {
        val routes = listOf(
            NavRoutes.SPLASH,
            NavRoutes.MENU,
            NavRoutes.CATEGORIES,
            NavRoutes.DIFFICULTY,
            NavRoutes.SETTINGS,
            NavRoutes.QUIZ_SCREEN,
            NavRoutes.RESULTS,
        )

        assertEquals(routes.size, routes.distinct().size)
        assertTrue(routes.all { it.isNotBlank() })
    }

    @Test
    fun quizAndResultRoutes_matchExpectedNames() {
        assertEquals("quiz", NavRoutes.QUIZ_SCREEN)
        assertEquals("results", NavRoutes.RESULTS)
        assertEquals("difficulty", NavRoutes.DIFFICULTY)
    }
}
