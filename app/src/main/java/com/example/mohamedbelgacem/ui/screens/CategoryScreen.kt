package com.example.mohamedbelgacem.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.mohamedbelgacem.data.model.Category
import com.example.mohamedbelgacem.ui.components.BottomNavBar
import com.example.mohamedbelgacem.ui.components.NavTab
import com.example.mohamedbelgacem.ui.theme.*

private data class CategoryUi(
    val category: Category,
    val emoji: String,
    val title: String,
    val subtitle: String,
    val tint: Color,
)

private val categoryList = listOf(
    CategoryUi(Category.ROMAN,   "🏛️", "Roman Heritage",    "El Jem, Carthage",    Color(0xFFC9A020)),
    CategoryUi(Category.ISLAMIC, "🕌", "Islamic Heritage",  "Kairouan, Sousse",    Color(0xFF5BA3C9)),
    CategoryUi(Category.PUNIC,   "⛵", "Punic & Pre-Roman", "Kerkouane, Utique",   Color(0xFF6DBF82)),
    CategoryUi(Category.MODERN,  "🏙️", "Modern Heritage",  "Habib Bourguiba",     Color(0xFFE07C54)),
    CategoryUi(Category.NATURAL, "🏔️", "Natural Sites",    "Ichkeul, Sahara",     Color(0xFF9B6DBF)),
    CategoryUi(Category.CITIES,  "🌆", "Cities",            "Medinas, Hubs",       Color(0xFF5CB8B2)),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    onCategorySelected: (Category) -> Unit,
    onNavigateToMenu: () -> Unit,
    onNavigateToSettings: () -> Unit = {},
    currentRoute: String,
) {
    var triggerAnim by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(80)
        triggerAnim = true
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Questini",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateToMenu) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back",
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
                currentTab = NavTab.CATEGORIES,
                onTabSelected = { tab ->
                    when (tab) {
                        NavTab.HOME -> onNavigateToMenu()
                        NavTab.CATEGORIES -> {}
                        NavTab.SETTINGS -> onNavigateToSettings()
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
                .padding(horizontal = 20.dp, vertical = 8.dp),
        ) {
            Text(
                text = "Choose Your Era",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Embark on a journey through Tunisia's 3,000-year-old history.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(20.dp))

            // 2-column grid of categories
            categoryList.chunked(2).forEachIndexed { rowIndex, row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    row.forEachIndexed { colIndex, item ->
                        val index = rowIndex * 2 + colIndex
                        CategoryCard(
                            item = item,
                            triggerAnim = triggerAnim,
                            staggerIndex = index,
                            modifier = Modifier.weight(1f),
                            onClick = { onCategorySelected(item.category) },
                        )
                    }
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.height(12.dp))
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun CategoryCard(
    item: CategoryUi,
    triggerAnim: Boolean,
    staggerIndex: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val scale by animateFloatAsState(
        targetValue = if (triggerAnim) 1f else 0.82f,
        animationSpec = tween(400, delayMillis = staggerIndex * 70, easing = FastOutSlowInEasing),
        label = "scale_$staggerIndex",
    )
    val alpha by animateFloatAsState(
        targetValue = if (triggerAnim) 1f else 0f,
        animationSpec = tween(350, delayMillis = staggerIndex * 70),
        label = "alpha_$staggerIndex",
    )

    Card(
        modifier = modifier
            .height(155.dp)
            .scale(scale)
            .alpha(alpha)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = NavyCard),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(item.tint.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = item.emoji,
                    fontSize = 24.sp,
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.labelLarge,
                color = item.tint,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
            )
            Spacer(Modifier.height(3.dp))
            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                fontSize = 11.sp,
            )
        }
    }
}
