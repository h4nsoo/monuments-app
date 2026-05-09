package com.example.mohamedbelgacem.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mohamedbelgacem.data.loader.getImageResId
import com.example.mohamedbelgacem.data.model.Question
import com.example.mohamedbelgacem.ui.components.BottomNavBar
import com.example.mohamedbelgacem.ui.components.NavTab
import com.example.mohamedbelgacem.ui.theme.*
import com.example.mohamedbelgacem.util.HapticFeedbackManager
import com.example.mohamedbelgacem.viewmodel.QuizUiState
import com.example.mohamedbelgacem.viewmodel.QuizViewModel
import androidx.compose.foundation.Image
import kotlinx.coroutines.delay

private enum class AnswerState { DEFAULT, SELECTED_CORRECT, SELECTED_WRONG, REVEALED_CORRECT }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onNavigateToResults: () -> Unit,
    onNavigateToMenu: () -> Unit,
) {
    val quizState by viewModel.quizUiState.collectAsStateWithLifecycle()
    val questions by viewModel.questions.collectAsStateWithLifecycle()
    val currentIndex by viewModel.currentIndex.collectAsStateWithLifecycle()
    val userAnswers by viewModel.userAnswers.collectAsStateWithLifecycle()
    val answerRevealed by viewModel.answerRevealed.collectAsStateWithLifecycle()
    val timeRemaining by viewModel.timeRemaining.collectAsStateWithLifecycle()
    val timerEnabled by viewModel.timerEnabled.collectAsStateWithLifecycle()
    val showingFeedback by viewModel.showingFeedback.collectAsStateWithLifecycle()
    val lastAnswerCorrect by viewModel.lastAnswerCorrect.collectAsStateWithLifecycle()
    val feedbackCorrectText by viewModel.feedbackCorrectText.collectAsStateWithLifecycle()
    val feedbackAnswerGiven by viewModel.feedbackAnswerGiven.collectAsStateWithLifecycle()
    val audioEnabled by viewModel.audioEnabled.collectAsStateWithLifecycle()
    val score by viewModel.score.collectAsStateWithLifecycle()

    LaunchedEffect(quizState) {
        if (quizState is QuizUiState.Completed) {
            onNavigateToResults()
        }
    }

    if (questions.isEmpty()) {
        Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = GoldAccent)
        }
        return
    }

    val question = questions.getOrNull(currentIndex) ?: return
    val selectedAnswerIndex = userAnswers[currentIndex]
    val progress = (currentIndex + 1).toFloat() / questions.size.toFloat()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text(
                                "Questini",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateToMenu) {
                            Icon(Icons.Default.Menu, null,
                                tint = MaterialTheme.colorScheme.onBackground)
                        }
                    },
                    actions = {
                        Box(
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(NavyCard)
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                        ) {
                            Text(
                                text = "$score pts",
                                fontSize = 13.sp,
                                color = GoldAccent,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                    ),
                )
                val animatedProgress by animateFloatAsState(
                    targetValue = progress,
                    animationSpec = tween(400),
                    label = "progress",
                )
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.fillMaxWidth(),
                    color = GoldAccent,
                    trackColor = NavyMedium,
                )
            }
        },
        bottomBar = {
            BottomNavBar(
                currentTab = NavTab.HOME,
                onTabSelected = { tab ->
                    when (tab) {
                        NavTab.HOME -> onNavigateToMenu()
                        NavTab.CATEGORIES -> onNavigateToMenu()
                        NavTab.SETTINGS -> onNavigateToMenu()
                    }
                },
            )
        },
    ) { padding ->
        AnimatedContent(
            targetState = showingFeedback,
            transitionSpec = {
                if (targetState) {
                    (scaleIn(tween(280), 0.94f) + fadeIn(tween(280)))
                        .togetherWith(fadeOut(tween(180)))
                } else {
                    fadeIn(tween(250)).togetherWith(fadeOut(tween(180)))
                }
            },
            label = "screenMode",
        ) { showing ->
            if (showing) {
                FeedbackContent(
                    isCorrect = lastAnswerCorrect,
                    answerGiven = feedbackAnswerGiven,
                    correctAnswerText = feedbackCorrectText,
                    pointsEarned = if (lastAnswerCorrect) 10 else 0,
                    audioEnabled = audioEnabled,
                    onDismiss = { viewModel.dismissFeedback() },
                    modifier = Modifier.padding(padding),
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState()),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column {
                            Text(
                                text = "HISTORICAL QUEST",
                                fontSize = 10.sp,
                                letterSpacing = 1.5.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Medium,
                            )
                            Text(
                                text = "Question ${currentIndex + 1}/${questions.size}",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        if (timerEnabled) {
                            TimerBadge(timeRemaining = timeRemaining)
                        }
                    }

                    AnimatedContent(
                        targetState = question,
                        transitionSpec = {
                            (slideInHorizontally { it / 2 } + fadeIn(tween(280))).togetherWith(
                                slideOutHorizontally { -it / 2 } + fadeOut(tween(200))
                            )
                        },
                        label = "questionContent",
                    ) { q ->
                        QuestionContent(
                            question = q,
                            selectedAnswerIndex = selectedAnswerIndex,
                            answerRevealed = answerRevealed,
                            onAnswerSelected = { viewModel.selectAnswer(it) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FeedbackContent(
    isCorrect: Boolean,
    answerGiven: Boolean,
    correctAnswerText: String,
    pointsEarned: Int,
    audioEnabled: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        HapticFeedbackManager.trigger(context, isCorrect, audioEnabled)
        delay(1800)
        onDismiss()
    }

    var triggered by remember { mutableStateOf(false) }
    val iconScale by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = spring(dampingRatio = 0.35f, stiffness = 260f),
        label = "iconScale",
    )
    val contentAlpha by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(380, delayMillis = 180),
        label = "contentAlpha",
    )
    LaunchedEffect(Unit) { triggered = true }

    var countdownProgress by remember { mutableFloatStateOf(1f) }
    val animatedCountdown by animateFloatAsState(
        targetValue = countdownProgress,
        animationSpec = tween(1800, easing = LinearEasing),
        label = "countdown",
    )
    LaunchedEffect(Unit) { countdownProgress = 0f }

    val titleText = when {
        isCorrect -> "Correct!"
        !answerGiven -> "Time's Up!"
        else -> "Incorrect!"
    }
    val accentColor = if (isCorrect) SuccessGreen else ErrorRed

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(indication = null, interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }) { onDismiss() },
    ) {
        LinearProgressIndicator(
            progress = { animatedCountdown },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            color = accentColor,
            trackColor = NavyMedium,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .scale(iconScale)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.14f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(58.dp),
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = titleText,
                fontSize = 34.sp,
                color = accentColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.alpha(contentAlpha),
            )

            Spacer(Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isCorrect) GoldAccent.copy(alpha = 0.16f) else NavyMedium)
                    .padding(horizontal = 22.dp, vertical = 8.dp)
                    .alpha(contentAlpha),
            ) {
                Text(
                    text = if (pointsEarned > 0) "+$pointsEarned PTS" else "+0 PTS",
                    fontSize = 14.sp,
                    color = if (isCorrect) GoldAccent else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                )
            }

            if (!isCorrect && correctAnswerText.isNotEmpty()) {
                Spacer(Modifier.height(30.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(NavyCard)
                        .padding(16.dp)
                        .alpha(contentAlpha),
                ) {
                    Text(
                        text = "CORRECT ANSWER",
                        fontSize = 10.sp,
                        letterSpacing = 1.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium,
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = SuccessGreen,
                            modifier = Modifier.size(20.dp),
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = correctAnswerText,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }

            Spacer(Modifier.height(52.dp))

            Text(
                text = "Tap anywhere to continue",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.alpha(contentAlpha * 0.55f),
            )
        }
    }
}

@Composable
private fun TimerBadge(timeRemaining: Int) {
    val isLow = timeRemaining <= 5
    val pulse by rememberInfiniteTransition(label = "pulse").animateFloat(
        initialValue = 1f,
        targetValue = if (isLow) 0.6f else 1f,
        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse),
        label = "pulse",
    )
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(NavyCard)
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .alpha(pulse),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(
            Icons.Default.Timer,
            contentDescription = null,
            tint = if (isLow) ErrorRed else GoldAccent,
            modifier = Modifier.size(16.dp),
        )
        Text(
            text = "0:%02d".format(timeRemaining),
            style = MaterialTheme.typography.labelLarge,
            color = if (isLow) ErrorRed else GoldAccent,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun QuestionContent(
    question: Question,
    selectedAnswerIndex: Int?,
    answerRevealed: Boolean,
    onAnswerSelected: (Int) -> Unit,
) {
    val context = LocalContext.current
    val imageResId = remember(question.image) {
        getImageResId(context, question.image)
    }

    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(NavyCard),
        ) {
            if (imageResId != 0) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("🏛️", fontSize = 48.sp)
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(GoldAccent)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
            ) {
                Text(
                    text = "⚑ 10 PTS",
                    fontSize = 11.sp,
                    color = NavyDeep,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        Spacer(Modifier.height(18.dp))

        Text(
            text = question.question,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(20.dp))

        question.options.forEachIndexed { index, option ->
            val state = resolveAnswerState(index, selectedAnswerIndex, question.correctAnswer, answerRevealed)
            AnswerOptionButton(
                letter = ('A' + index).toString(),
                text = option,
                state = state,
                onClick = { onAnswerSelected(index) },
            )
            Spacer(Modifier.height(10.dp))
        }

        Spacer(Modifier.height(16.dp))
    }
}

private fun resolveAnswerState(
    index: Int,
    selected: Int?,
    correct: Int,
    revealed: Boolean,
): AnswerState {
    if (!revealed || selected == null) return AnswerState.DEFAULT
    return when {
        index == selected && index == correct -> AnswerState.SELECTED_CORRECT
        index == selected && index != correct -> AnswerState.SELECTED_WRONG
        index == correct && selected != correct -> AnswerState.REVEALED_CORRECT
        else -> AnswerState.DEFAULT
    }
}

@Composable
private fun AnswerOptionButton(
    letter: String,
    text: String,
    state: AnswerState,
    onClick: () -> Unit,
) {
    val bgColor = when (state) {
        AnswerState.DEFAULT          -> NavyCard
        AnswerState.SELECTED_CORRECT -> GoldAccent
        AnswerState.SELECTED_WRONG   -> ErrorRed.copy(alpha = 0.85f)
        AnswerState.REVEALED_CORRECT -> SuccessGreen.copy(alpha = 0.25f)
    }
    val textColor = when (state) {
        AnswerState.DEFAULT          -> MaterialTheme.colorScheme.onSurface
        AnswerState.SELECTED_CORRECT -> NavyDeep
        AnswerState.SELECTED_WRONG   -> Color.White
        AnswerState.REVEALED_CORRECT -> SuccessGreen
    }
    val borderColor = when (state) {
        AnswerState.REVEALED_CORRECT -> SuccessGreen
        else -> Color.Transparent
    }

    val animBg by animateColorAsState(bgColor, tween(250), label = "bg")
    val animText by animateColorAsState(textColor, tween(250), label = "text")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(animBg)
            .border(
                width = if (state == AnswerState.REVEALED_CORRECT) 1.5.dp else 0.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp),
            )
            .clickable(enabled = state == AnswerState.DEFAULT, onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    when (state) {
                        AnswerState.SELECTED_CORRECT -> NavyDeep.copy(alpha = 0.3f)
                        AnswerState.SELECTED_WRONG   -> Color.White.copy(alpha = 0.2f)
                        else -> NavyMedium
                    }
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = letter,
                color = animText,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
            )
        }
        Spacer(Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = animText,
            modifier = Modifier.weight(1f),
        )
        if (state == AnswerState.SELECTED_CORRECT) {
            Icon(Icons.Default.CheckCircle, null, tint = NavyDeep, modifier = Modifier.size(20.dp))
        } else if (state == AnswerState.SELECTED_WRONG) {
            Icon(Icons.Default.Cancel, null, tint = Color.White, modifier = Modifier.size(20.dp))
        } else if (state == AnswerState.REVEALED_CORRECT) {
            Icon(Icons.Default.CheckCircle, null, tint = SuccessGreen, modifier = Modifier.size(20.dp))
        }
    }
}
