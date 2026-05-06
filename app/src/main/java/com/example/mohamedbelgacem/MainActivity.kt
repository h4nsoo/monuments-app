package com.example.mohamedbelgacem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.mohamedbelgacem.navigation.AppNavigation
import com.example.mohamedbelgacem.navigation.NavRoutes
import com.example.mohamedbelgacem.ui.theme.TunisiaHeritageQuestTheme

/**
 * Main Activity for Tunisia Heritage Quest App
 *
 * Entry point for the application.
 * Sets up:
 * - Edge-to-edge display
 * - Navigation controller
 * - App theme (TunisiaHeritageQuestTheme)
 * - App navigation graph
 *
 * The app starts with SplashScreen and navigates through various screens
 * based on user interactions.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge rendering
        enableEdgeToEdge()

        setContent {
            // Apply Tunisia Heritage Quest Theme
            TunisiaHeritageQuestTheme(darkTheme = false) {
                // Create navigation controller
                val navController = rememberNavController()

                // Surface with background color from theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Set up app navigation with splash screen as start destination
                    AppNavigation(
                        navController = navController,
                        startDestination = NavRoutes.SPLASH
                    )
                }
            }
        }
    }
}