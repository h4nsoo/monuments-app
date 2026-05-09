package com.example.mohamedbelgacem.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mohamedbelgacem.data.model.Difficulty
import com.example.mohamedbelgacem.ui.screens.*
import com.example.mohamedbelgacem.viewmodel.QuizViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: QuizViewModel,
    startDestination: String = NavRoutes.SPLASH,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideInHorizontally(tween(300)) { it } + fadeIn(tween(300))
        },
        exitTransition = {
            slideOutHorizontally(tween(280)) { -it / 4 } + fadeOut(tween(200))
        },
        popEnterTransition = {
            slideInHorizontally(tween(300)) { -it } + fadeIn(tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(tween(280)) { it } + fadeOut(tween(200))
        },
    ) {

        // Splash
        composable(
            route = NavRoutes.SPLASH,
            enterTransition = { fadeIn(tween(400)) },
            exitTransition  = { fadeOut(tween(500)) },
        ) {
            SplashScreen(
                onNavigateToMenu = {
                    navController.navigate(NavRoutes.MENU) {
                        popUpTo(NavRoutes.SPLASH) { inclusive = true }
                    }
                },
            )
        }

        // Main Menu
        composable(route = NavRoutes.MENU) {
            MainMenuScreen(
                onNavigateToCategories = { navController.navigate(NavRoutes.CATEGORIES) },
                onNavigateToSettings   = { navController.navigate(NavRoutes.SETTINGS) },
                onStartQuiz = {
                    viewModel.initializeQuiz()
                    navController.navigate(NavRoutes.QUIZ_SCREEN)
                },
                currentRoute = NavRoutes.MENU,
            )
        }

        // Categories
        composable(route = NavRoutes.CATEGORIES) {
            CategoryScreen(
                onCategorySelected = { category ->
                    viewModel.selectCategory(category)
                    navController.navigate(NavRoutes.DIFFICULTY)
                },
                onNavigateToMenu = {
                    navController.popBackStack(NavRoutes.MENU, inclusive = false)
                },
                onNavigateToSettings = { navController.navigate(NavRoutes.SETTINGS) },
                currentRoute = NavRoutes.CATEGORIES,
            )
        }

        // Difficulty — starts quiz after user picks a level
        composable(route = NavRoutes.DIFFICULTY) {
            DifficultyScreen(
                onDifficultySelected = { difficulty ->
                    viewModel.setDifficulty(difficulty)
                    viewModel.initializeQuiz()
                    navController.navigate(NavRoutes.QUIZ_SCREEN) {
                        popUpTo(NavRoutes.DIFFICULTY) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack(NavRoutes.CATEGORIES, inclusive = false)
                },
                onNavigateToMenu = {
                    navController.popBackStack(NavRoutes.MENU, inclusive = false)
                },
                onNavigateToSettings = {
                    navController.navigate(NavRoutes.SETTINGS)
                },
                currentRoute = NavRoutes.DIFFICULTY,
            )
        }

        // Settings — general app settings only
        composable(route = NavRoutes.SETTINGS) {
            SettingsScreen(
                onNavigateToMenu = {
                    navController.popBackStack(NavRoutes.MENU, inclusive = false)
                },
                onNavigateToCategories = {
                    navController.navigate(NavRoutes.CATEGORIES) {
                        popUpTo(NavRoutes.CATEGORIES) { inclusive = true }
                    }
                },
            )
        }

        // Quiz
        composable(
            route = NavRoutes.QUIZ_SCREEN,
            enterTransition = {
                scaleIn(tween(350), initialScale = 0.92f) + fadeIn(tween(350))
            },
            exitTransition  = { fadeOut(tween(250)) },
            popExitTransition = {
                slideOutHorizontally(tween(300)) { it } + fadeOut(tween(250))
            },
        ) {
            QuizScreen(
                viewModel = viewModel,
                onNavigateToResults = {
                    navController.navigate(NavRoutes.RESULTS) {
                        popUpTo(NavRoutes.QUIZ_SCREEN) { inclusive = true }
                    }
                },
                onNavigateToMenu = {
                    viewModel.resetQuiz()
                    navController.popBackStack(NavRoutes.MENU, inclusive = false)
                },
            )
        }

        // Results
        composable(
            route = NavRoutes.RESULTS,
            enterTransition = {
                scaleIn(tween(400), initialScale = 0.88f) + fadeIn(tween(400))
            },
            exitTransition  = { fadeOut(tween(300)) },
        ) {
            ResultScreen(
                viewModel = viewModel,
                onPlayAgain = {
                    // Restart with the same category & difficulty — no navigation detour needed
                    viewModel.resetQuiz()
                    viewModel.initializeQuiz()
                    navController.navigate(NavRoutes.QUIZ_SCREEN) {
                        popUpTo(NavRoutes.RESULTS) { inclusive = true }
                    }
                },
                onBackToMenu = {
                    viewModel.resetQuiz()
                    navController.popBackStack(NavRoutes.MENU, inclusive = false)
                },
            )
        }
    }
}
