package com.example.mohamedbelgacem.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mohamedbelgacem.ui.screens.CategoryScreen
import com.example.mohamedbelgacem.ui.screens.MainMenuScreen
import com.example.mohamedbelgacem.ui.screens.QuizScreen
import com.example.mohamedbelgacem.ui.screens.ResultScreen
import com.example.mohamedbelgacem.ui.screens.SettingsScreen
import com.example.mohamedbelgacem.ui.screens.SplashScreen

/**
 * App Navigation Setup
 *
 * Defines the navigation graph for the Tunisia Heritage Quest app.
 * Handles route definitions and screen transitions.
 *
 * Routes:
 * - splash: Initial splash screen
 * - menu: Main menu screen
 * - categories: Category selection screen
 * - settings: Settings screen
 * - quiz/{category}/{difficulty}: Quiz screen with parameters
 * - results: Results display screen
 *
 * @param navController The NavHostController managing navigation
 * @param startDestination Initial screen to display
 */
@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = NavRoutes.SPLASH
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash Screen
        composable(route = NavRoutes.SPLASH) {
            SplashScreen()
            // TODO: Add LaunchedEffect to navigate to MENU after delay
        }

        // Main Menu Screen
        composable(route = NavRoutes.MENU) {
            MainMenuScreen()
        }

        // Category Selection Screen
        composable(route = NavRoutes.CATEGORIES) {
            CategoryScreen()
        }

        // Settings Screen
        composable(route = NavRoutes.SETTINGS) {
            SettingsScreen()
        }

        // Quiz Screen with dynamic parameters
        composable(
            route = NavRoutes.QUIZ,
            arguments = listOf(
                androidx.navigation.navArgument("category") {
                    type = androidx.navigation.NavType.StringType
                },
                androidx.navigation.navArgument("difficulty") {
                    type = androidx.navigation.NavType.StringType
                }
            )
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "History"
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "Medium"
            QuizScreen(category = category, difficulty = difficulty)
        }

        // Results Screen
        composable(route = NavRoutes.RESULTS) {
            ResultScreen()
        }
    }
}
