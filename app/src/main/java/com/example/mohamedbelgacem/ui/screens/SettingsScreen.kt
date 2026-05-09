package com.example.mohamedbelgacem.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mohamedbelgacem.ui.components.BottomNavBar
import com.example.mohamedbelgacem.ui.components.NavTab
import com.example.mohamedbelgacem.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateToMenu: () -> Unit,
    onNavigateToCategories: () -> Unit,
) {
    var audioEnabled by remember { mutableStateOf(true) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var selectedLanguage by remember { mutableStateOf("English") }
    var showLanguageDialog by remember { mutableStateOf(false) }

    var visible by remember { mutableStateOf(false) }
    val contentAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(400),
        label = "alpha",
    )
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(60)
        visible = true
    }

    if (showLanguageDialog) {
        LanguageDialog(
            current = selectedLanguage,
            onSelect = { selectedLanguage = it; showLanguageDialog = false },
            onDismiss = { showLanguageDialog = false },
        )
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
                    IconButton(onClick = onNavigateToMenu) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
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
                currentTab = NavTab.SETTINGS,
                onTabSelected = { tab ->
                    when (tab) {
                        NavTab.HOME -> onNavigateToMenu()
                        NavTab.CATEGORIES -> onNavigateToCategories()
                        NavTab.SETTINGS -> {}
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
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .alpha(contentAlpha),
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Manage your app preferences.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(Modifier.height(24.dp))

            // --- Experience ---
            SectionLabel("Experience")
            Spacer(Modifier.height(8.dp))

            SettingsGroup {
                RowClickable(
                    icon = Icons.Default.Language,
                    iconTint = GoldAccent,
                    label = "Language",
                    sublabel = selectedLanguage,
                    onClick = { showLanguageDialog = true },
                    trailingContent = {
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp),
                        )
                    },
                )
                SettingsDivider()
                RowToggle(
                    icon = Icons.AutoMirrored.Filled.VolumeUp,
                    iconTint = Color(0xFF5BA3C9),
                    label = "Audio",
                    sublabel = "Sound effects and music",
                    checked = audioEnabled,
                    onToggle = { audioEnabled = it },
                )
            }

            Spacer(Modifier.height(20.dp))

            // --- Notifications ---
            SectionLabel("Notifications")
            Spacer(Modifier.height(8.dp))

            SettingsGroup {
                RowToggle(
                    icon = Icons.Default.Notifications,
                    iconTint = Color(0xFF6DBF82),
                    label = "Daily Challenge",
                    sublabel = "Remind me to play each day",
                    checked = notificationsEnabled,
                    onToggle = { notificationsEnabled = it },
                )
            }

            Spacer(Modifier.height(20.dp))

            // --- Appearance ---
            SectionLabel("Appearance")
            Spacer(Modifier.height(8.dp))

            SettingsGroup {
                RowInfo(
                    icon = Icons.Default.DarkMode,
                    iconTint = Color(0xFF9B6DBF),
                    label = "Theme",
                    value = "Dark",
                )
            }

            Spacer(Modifier.height(20.dp))

            // --- About ---
            SectionLabel("About")
            Spacer(Modifier.height(8.dp))

            SettingsGroup {
                RowInfo(
                    icon = Icons.Default.Info,
                    iconTint = MaterialTheme.colorScheme.onSurfaceVariant,
                    label = "Version",
                    value = "1.0.0",
                )
                SettingsDivider()
                RowInfo(
                    icon = Icons.Default.Public,
                    iconTint = MaterialTheme.colorScheme.onSurfaceVariant,
                    label = "Made with ❤️ in Tunisia",
                    value = "",
                )
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

// --- Sub-composables ---

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text.uppercase(),
        fontSize = 11.sp,
        letterSpacing = 1.5.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
private fun SettingsGroup(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(NavyCard),
        content = content,
    )
}

@Composable
private fun SettingsDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(start = 56.dp),
        color = NavyMedium,
        thickness = 0.5.dp,
    )
}

@Composable
private fun RowClickable(
    icon: ImageVector,
    iconTint: Color,
    label: String,
    sublabel: String,
    onClick: () -> Unit,
    trailingContent: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconBox(icon, iconTint)
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Medium)
            if (sublabel.isNotEmpty()) {
                Text(sublabel, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        trailingContent()
    }
}

@Composable
private fun RowToggle(
    icon: ImageVector,
    iconTint: Color,
    label: String,
    sublabel: String,
    checked: Boolean,
    onToggle: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconBox(icon, iconTint)
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Medium)
            Text(sublabel, style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(
            checked = checked,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = LightBlue,
                uncheckedThumbColor = Color.White.copy(alpha = 0.5f),
                uncheckedTrackColor = NavyMedium,
            ),
        )
    }
}

@Composable
private fun RowInfo(
    icon: ImageVector,
    iconTint: Color,
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconBox(icon, iconTint)
        Spacer(Modifier.width(14.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f),
        )
        if (value.isNotEmpty()) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun IconBox(icon: ImageVector, tint: Color) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(tint.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(18.dp))
    }
}

// --- Language Dialog ---

@Composable
private fun LanguageDialog(
    current: String,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val languages = listOf(
        "English" to "🇬🇧",
        "Français" to "🇫🇷",
        "العربية" to "🇹🇳",
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = NavyCard,
        title = {
            Text(
                "Select Language",
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            Column {
                languages.forEach { (lang, flag) ->
                    val isSelected = lang == current
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) GoldAccent.copy(alpha = 0.15f) else Color.Transparent)
                            .clickable { onSelect(lang) }
                            .padding(horizontal = 12.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text(flag, fontSize = 22.sp)
                        Text(
                            text = lang,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (isSelected) GoldAccent else MaterialTheme.colorScheme.onBackground,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            modifier = Modifier.weight(1f),
                        )
                        if (isSelected) {
                            Icon(Icons.Default.Check, null,
                                tint = GoldAccent, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
    )
}
