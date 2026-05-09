# Questini Project Summary

## Project Overview

Questini is an Android quiz application built with Kotlin and Jetpack Compose. The project is in a stable working state and includes the main quiz flow, a dedicated difficulty step, JSON-backed questions, and a polished Compose-based UI foundation.

Verified status:

- `compileDebugKotlin`: successful
- `testDebugUnitTest`: successful
- `compileDebugAndroidTestKotlin`: successful
- `connectedDebugAndroidTest`: blocked by device install policy (`INSTALL_FAILED_USER_RESTRICTED`), not by compilation issues

Current content focus:

- Heritage-themed quiz experience
- Cities dataset loaded from assets
- Category and difficulty selection before quiz start
- Clean branding and launcher icon integration

## Architecture Explanation

The app follows a layered Android architecture with clear separation of responsibilities:

- Presentation layer: Compose screens for splash, menu, categories, difficulty, quiz, settings, and results
- Navigation layer: a centralized Compose navigation graph with route constants in `NavRoutes`
- State layer: `QuizViewModel` uses observable state to manage quiz flow, scoring, timers, and feedback
- Data layer: `QuestionLoader` reads questions from JSON assets, and `QuestionRepository` filters questions by category and difficulty

This structure improves maintainability, testability, and feature isolation. The current flow is:

1. Splash
2. Menu
3. Categories
4. Difficulty
5. Quiz
6. Results

The difficulty screen appears after category selection so the user must choose both dimensions before the quiz starts.

## Setup Instruction

To work with the project locally:

1. Open the project root in Android Studio or VS Code.
2. Ensure the Android SDK is available at the path configured in `local.properties`.
3. Build the app with Gradle using the wrapper.
4. Run unit tests with `testDebugUnitTest`.
5. Compile Android tests with `compileDebugAndroidTestKotlin`.

Useful commands:

- `./gradlew assembleDebug`
- `./gradlew testDebugUnitTest`
- `./gradlew compileDebugAndroidTestKotlin`
- `./gradlew connectedDebugAndroidTest` when the connected device allows installation

Setup notes:

- The app launcher icon is set from `@drawable/logo`
- The active dataset is stored in `app/src/main/assets/roman_questions.json`
- The current quiz dataset contains 15 city questions with `EASY`, `MEDIUM`, and `HARD` difficulty levels

## Known Issues

- Connected Android tests cannot run on the current device because USB installation is restricted by the device policy.
- Some questions depend on drawable resources matching the JSON image names exactly; if a drawable is missing, the app falls back gracefully, but the image will not display.
- The current question asset filename is still `roman_questions.json` even though it now contains the Cities dataset.

Recommended next improvements:

- Add any missing city drawables used by the dataset
- Expand ViewModel-focused tests for quiz scoring and feedback
- Re-run connected instrumentation tests on a device without install restrictions
