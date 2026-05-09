package com.example.mohamedbelgacem.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mohamedbelgacem.ui.components.BottomNavBar
import com.example.mohamedbelgacem.ui.components.NavTab
import com.example.mohamedbelgacem.ui.theme.*
import com.example.mohamedbelgacem.viewmodel.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    viewModel: QuizViewModel,
    onPlayAgain: () -> Unit,
    onBackToMenu: () -> Unit,
) {
    val questions by viewModel.questions.collectAsStateWithLifecycle()
    val totalElapsed by viewModel.totalElapsedSeconds.collectAsStateWithLifecycle()

    val score = remember { viewModel.getScore() }
    val totalPoints = remember { viewModel.score.value }
    val total = remember { questions.size }
    val incorrect = remember { viewModel.getIncorrectCount() }
    val percentage = if (total > 0) (score * 100 / total) else 0
    val elapsed = totalElapsed

    // Medal bounce animation
    var triggered by remember { mutableStateOf(false) }
    val medalScale by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = spring(dampingRatio = 0.4f, stiffness = 300f),
        label = "medal",
    )
    val contentAlpha by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(500, delayMillis = 300),
        label = "alpha",
    )

    // Animated score counter
    var displayedScore by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        triggered = true
        repeat(score) { i ->
            kotlinx.coroutines.delay(100L)
            displayedScore = i + 1
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
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
                    IconButton(onClick = onBackToMenu) {
                        Icon(Icons.Default.Menu, null,
                            tint = MaterialTheme.colorScheme.onBackground)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
            )
        },
        bottomBar = {
            BottomNavBar(
                currentTab = NavTab.HOME,
                onTabSelected = { tab ->
                    when (tab) {
                        NavTab.HOME -> onBackToMenu()
                        NavTab.CATEGORIES -> onBackToMenu()
                        NavTab.SETTINGS -> onBackToMenu()
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // --- Score Card ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = NavyCard),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // Medal
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .scale(medalScale)
                            .clip(CircleShape)
                            .background(GoldAccent.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("🏅", fontSize = 38.sp)
                    }

                    Spacer(Modifier.height(14.dp))

                    Text(
                        text = "QUIZ COMPLETED",
                        fontSize = 11.sp,
                        letterSpacing = 2.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.alpha(contentAlpha),
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = resultTitle(percentage),
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.alpha(contentAlpha),
                    )

                    Spacer(Modifier.height(16.dp))

                    // Big score
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.alpha(contentAlpha),
                    ) {
                        Text(
                            text = displayedScore.toString(),
                            fontSize = 72.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            lineHeight = 72.sp,
                        )
                        Text(
                            text = "/$total",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 10.dp),
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    // Percentage + points badges
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.alpha(contentAlpha),
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(NavyMedium)
                                .padding(horizontal = 16.dp, vertical = 6.dp),
                        ) {
                            Text(
                                text = "$percentage% SCORE",
                                fontSize = 12.sp,
                                letterSpacing = 1.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(GoldAccent.copy(alpha = 0.16f))
                                .padding(horizontal = 16.dp, vertical = 6.dp),
                        ) {
                            Text(
                                text = "$totalPoints PTS",
                                fontSize = 12.sp,
                                letterSpacing = 1.sp,
                                color = GoldAccent,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // --- Correct / Incorrect ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(contentAlpha),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    icon = { Icon(Icons.Default.CheckCircle, null,
                        tint = SuccessGreen, modifier = Modifier.size(26.dp)) },
                    label = "Correct",
                    value = score.toString(),
                    valueColor = Color.White,
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    icon = { Icon(Icons.Default.Cancel, null,
                        tint = ErrorRed, modifier = Modifier.size(26.dp)) },
                    label = "Incorrect",
                    value = incorrect.toString(),
                    valueColor = Color.White,
                )
            }

            Spacer(Modifier.height(12.dp))

            // --- Time Card ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(contentAlpha),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavyCard),
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(NavyMedium),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(Icons.Default.Timer, null, tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(24.dp))
                    }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "TIME TAKEN",
                            fontSize = 10.sp,
                            letterSpacing = 1.5.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(
                            text = formatElapsed(elapsed),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // --- Actions ---
            Button(
                onClick = onPlayAgain,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SkyBlue,
                    contentColor = NavyDeep,
                ),
            ) {
                Icon(Icons.Default.Refresh, null)
                Spacer(Modifier.width(8.dp))
                Text("Play Again", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onBackToMenu,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NavyCard,
                    contentColor = Color.White,
                ),
            ) {
                Icon(Icons.Default.Home, null)
                Spacer(Modifier.width(8.dp))
                Text("Back to Menu", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier,
    icon: @Composable () -> Unit,
    label: String,
    value: String,
    valueColor: Color,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NavyCard),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            icon()
            Spacer(Modifier.height(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = valueColor,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

private fun resultTitle(percentage: Int) = when {
    percentage >= 80 -> "Heritage Master"
    percentage >= 60 -> "Heritage Expert"
    percentage >= 40 -> "Heritage Seeker"
    else             -> "Heritage Novice"
}

private fun formatElapsed(seconds: Long): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%02d:%02d min".format(m, s)
}
