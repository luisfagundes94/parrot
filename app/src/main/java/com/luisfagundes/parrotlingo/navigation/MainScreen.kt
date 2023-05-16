package com.luisfagundes.parrotlingo.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.luisfagundes.framework.utils.commonNavigationOptions
import com.luisfagundes.parrotlingo.navigation.graphs.MainNavGraph

@Composable
fun MainScreen(
    navHostController: NavHostController = rememberNavController(),
) {
    Scaffold(
        bottomBar = { BottomBar(navHostController = navHostController) },
    ) { padding ->
        MainNavGraph(
            navController = navHostController,
            modifier = Modifier.padding(padding),
        )
    }
}

@Composable
fun BottomBar(
    navHostController: NavHostController,
) {
    val screens = listOf(
        BottomBarScreen.Translation,
        BottomBarScreen.Saved,
        BottomBarScreen.Settings,
    )
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = screens.any {
        it.route == currentDestination?.route
    }

    if (bottomBarDestination) {
        NavigationBar {
            screens.forEach { screen ->
                NavBarItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navHostController = navHostController,
                )
            }
        }
    }
}

@Composable
private fun RowScope.NavBarItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navHostController: NavHostController,
) {
    NavigationBarItem(
        label = {
            Text(
                text = stringResource(screen.screenNameId),
                fontWeight = FontWeight.SemiBold,
            )
        },
        icon = {
            Icon(
                painter = rememberVectorPainter(image = screen.icon),
                contentDescription = stringResource(id = screen.screenNameId),
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navHostController.navigate(screen.route) {
                commonNavigationOptions(navHostController)
            }
        },
    )
}
