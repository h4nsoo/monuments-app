package com.example.mohamedbelgacem.navigation

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.mohamedbelgacem.viewmodel.QuizViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class AppNavigationTest {

    @get:org.junit.Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun appNavigation_movesBetweenRoutes() {
        lateinit var navController: TestNavHostController

        composeRule.setContent {
            val context = LocalContext.current
            navController = TestNavHostController(context).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            val viewModel = remember {
                QuizViewModel(ApplicationProvider.getApplicationContext<Application>())
            }

            AppNavigation(
                navController = navController,
                viewModel = viewModel,
                startDestination = NavRoutes.MENU,
            )
        }

        composeRule.waitForIdle()
        assertEquals(NavRoutes.MENU, navController.currentBackStackEntry?.destination?.route)

        composeRule.runOnIdle {
            navController.navigate(NavRoutes.CATEGORIES)
        }
        composeRule.waitForIdle()
        assertEquals(NavRoutes.CATEGORIES, navController.currentBackStackEntry?.destination?.route)
    }
}
