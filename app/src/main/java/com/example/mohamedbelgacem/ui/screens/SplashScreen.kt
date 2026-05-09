package com.example.mohamedbelgacem.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mohamedbelgacem.R
import com.example.mohamedbelgacem.ui.theme.GoldAccent
import com.example.mohamedbelgacem.ui.theme.NavyDark
import kotlinx.coroutines.delay
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

@Composable
fun SplashScreen(onNavigateToMenu: () -> Unit) {
    var contentVisible by remember { mutableStateOf(false) }
    var bottomVisible by remember { mutableStateOf(false) }

    val logoScale by animateFloatAsState(
        targetValue = if (contentVisible) 1f else 0.7f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 200f),
        label = "logoScale",
    )
    val contentAlpha by animateFloatAsState(
        targetValue = if (contentVisible) 1f else 0f,
        animationSpec = tween(700),
        label = "contentAlpha",
    )
    val bottomAlpha by animateFloatAsState(
        targetValue = if (bottomVisible) 1f else 0f,
        animationSpec = tween(600),
        label = "bottomAlpha",
    )

    // Pulsing dot animation for "DISCOVERY IN PROGRESS"
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "pulse",
    )

    LaunchedEffect(Unit) {
        delay(200)
        contentVisible = true
        delay(1000)
        bottomVisible = true
        delay(1600)
        onNavigateToMenu()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NavyDark),
    ) {
        // Subtle diamond pattern
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawDiamondPattern(this)
        }

        // Center content
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(contentAlpha)
                .scale(logoScale),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Questini",
                modifier = Modifier.size(90.dp),
            )
            Spacer(Modifier.height(28.dp))
            Text(
                text = "Questini",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Normal,
                fontSize = 52.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(14.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                HorizontalDivider(
                    modifier = Modifier.width(40.dp),
                    thickness = 1.dp,
                    color = GoldAccent,
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "HERITAGE EXPLORER",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 3.sp,
                    color = GoldAccent,
                )
                Spacer(Modifier.width(10.dp))
                HorizontalDivider(
                    modifier = Modifier.width(40.dp),
                    thickness = 1.dp,
                    color = GoldAccent,
                )
            }
        }

        // Bottom text
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 56.dp)
                .alpha(bottomAlpha),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .width(28.dp)
                    .height(2.dp)
                    .background(GoldAccent.copy(alpha = pulseAlpha)),
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "DISCOVERY IN PROGRESS",
                fontSize = 10.sp,
                letterSpacing = 2.sp,
                color = Color.White.copy(alpha = 0.55f),
                fontWeight = FontWeight.Normal,
            )
        }
    }
}

private fun drawDiamondPattern(scope: DrawScope) {
    val tileSize = 38f
    val color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.04f)
    val stroke = Stroke(width = 1f)
    var row = 0
    var y = -tileSize
    while (y < scope.size.height + tileSize) {
        val xOffset = if (row % 2 == 0) 0f else tileSize
        var x = -tileSize + xOffset
        while (x < scope.size.width + tileSize * 2) {
            val path = Path()
            path.moveTo(x, y - tileSize)
            path.lineTo(x + tileSize, y)
            path.lineTo(x, y + tileSize)
            path.lineTo(x - tileSize, y)
            path.close()
            scope.drawPath(path, color = color, style = stroke)
            x += tileSize * 2
        }
        y += tileSize
        row++
    }
}
