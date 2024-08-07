package com.llimapons.rickverse.designSystem.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry

@Composable
fun RickVerseBottomBar(
    items: List<BottomBarItem>,
    onItemClick: (BottomBarItem) -> Unit,
    currentRoute: State<NavBackStackEntry?>
){
    NavigationBar {
        items.forEach { item ->
            val selected = currentRoute.value?.destination?.route == item.route
            NavigationBarItem(
                selected = selected,
                label = { Text(text = item.title) },
                onClick = { onItemClick(item) },
                icon = {
                    Icon(
                        imageVector = if (selected) {
                            item.iconSelected
                        } else {
                            item.iconUnselected
                        },
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}

data class BottomBarItem(
    val title : String,
    val iconSelected : ImageVector,
    val iconUnselected : ImageVector,
    val route : String,
    )