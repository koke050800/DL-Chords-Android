package com.example.DLChordsTT.features.lists_music.ui.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.DLChordsTT.features.lists_music.ui.navigation.Destinations
import com.example.DLChordsTT.ui.theme.DLChordsColors
import com.example.DLChordsTT.ui.theme.DLChordsTheme

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<Destinations>
) {
    val currentRoute = currentRoute(navController)

    BottomNavigation(
        backgroundColor = DLChordsTheme.colors.primary,
        contentColor = DLChordsTheme.colors.onPrimary
    ) {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
                label = {
                    Text(
                        text = screen.title,
                        color = DLChordsTheme.colors.onPrimary,
                        maxLines = 1,
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}



@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}