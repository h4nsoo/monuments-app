package com.example.mohamedbelgacem.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class NavTab { HOME, CATEGORIES, SETTINGS }

private data class NavItem(val tab: NavTab, val icon: ImageVector, val label: String)

private val navItems = listOf(
    NavItem(NavTab.HOME,       Icons.Default.Home,     "Home"),
    NavItem(NavTab.CATEGORIES, Icons.Default.GridView, "Categories"),
    NavItem(NavTab.SETTINGS,   Icons.Default.Settings, "Settings"),
)

@Composable
fun BottomNavBar(
    currentTab: NavTab,
    onTabSelected: (NavTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .navigationBarsPadding()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        navItems.forEach { item ->
            val isSelected = currentTab == item.tab
            val iconTint by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.secondary
                              else MaterialTheme.colorScheme.onSurfaceVariant,
                animationSpec = tween(200),
                label = "iconTint",
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onTabSelected(item.tab) }
                    .padding(horizontal = 24.dp, vertical = 6.dp),
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = iconTint,
                    modifier = Modifier.size(22.dp),
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = item.label,
                    fontSize = 10.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                    color = iconTint,
                )
            }
        }
    }
}
