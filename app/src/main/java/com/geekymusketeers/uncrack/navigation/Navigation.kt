package com.geekymusketeers.uncrack.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.presentation.homeScreen.HomeScreen
import com.geekymusketeers.uncrack.presentation.passwordScreen.PasswordScreen
import com.geekymusketeers.uncrack.presentation.profileScreen.ProfileScreen
import com.geekymusketeers.uncrack.presentation.shieldScreen.ShieldScreen
import com.geekymusketeers.uncrack.ui.theme.Background
import com.geekymusketeers.uncrack.ui.theme.DMSansFontFamily
import com.geekymusketeers.uncrack.ui.theme.FadeIn
import com.geekymusketeers.uncrack.ui.theme.FadeOut
import com.geekymusketeers.uncrack.ui.theme.OnPrimaryContainer
import com.geekymusketeers.uncrack.ui.theme.OnSurface40
import com.geekymusketeers.uncrack.ui.theme.PrimaryDark

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation() {

    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()

    val screensWithoutNavigationBar = listOf(
        "onboarding_screen"
    )

    Scaffold(
        bottomBar = {
            ShowBottomNavigation(
                backStackEntry,
                screensWithoutNavigationBar,
                navController
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = "home_screen",
            enterTransition = { FadeIn },
            exitTransition = { FadeOut },
            popEnterTransition = { FadeIn },
            popExitTransition = { FadeOut }
        ) {

            composable(route = "home_screen") {
                HomeScreen()
            }

            composable(route = "password_screen") {
                PasswordScreen()
            }

            composable(route = "shield_screen") {
                ShieldScreen()
            }

            composable(route = "profile_screen") {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun ShowBottomNavigation(
    backStackEntry: State<NavBackStackEntry?>,
    screensWithoutNavigationBar: List<String>,
    navController: NavHostController
) {
    if (backStackEntry.value?.destination?.route !in screensWithoutNavigationBar) {
        NavigationBar(
            containerColor = Background
        ) {

            val bottomNavItems = listOf(
                BottomNavItem(
                    name = "Home",
                    route = "home_screen",
                    icon = ImageVector.vectorResource(id = R.drawable.home_icon)
                ),
                BottomNavItem(
                    name = "Passwords",
                    route = "password_screen",
                    icon = ImageVector.vectorResource(id = R.drawable.password)
                ),
                BottomNavItem(
                    name = "Shield",
                    route = "shield_screen",
                    icon = ImageVector.vectorResource(id = R.drawable.shield)
                ),
                BottomNavItem(
                    name = "Profile",
                    route = "profile_screen",
                    icon = ImageVector.vectorResource(id = R.drawable.person_icon)
                )
            )

            bottomNavItems.forEach { item ->
                NavigationBarItem(
                    alwaysShowLabel = true,
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name,
                            tint = if (backStackEntry.value?.destination?.route == item.route)
                                OnPrimaryContainer
                            else
                                OnSurface40
                        )
                    },
                    label = {
                        Text(
                            text = item.name,
                            fontFamily = DMSansFontFamily,
                            color = if (backStackEntry.value?.destination?.route == item.route)
                                OnPrimaryContainer
                            else
                                OnSurface40,
                            fontWeight = if (backStackEntry.value?.destination?.route == item.route)
                                FontWeight.SemiBold
                            else
                                FontWeight.Normal,
                        )
                    },
                    selected = backStackEntry.value?.destination?.route == item.route,
                    onClick = {
                        val currentDestination =
                            navController.currentBackStackEntry?.destination?.route
                        if (item.route != currentDestination) {
                            navController.navigate(item.route) {
                                navController.graph.findStartDestination().let { route ->
                                    popUpTo(route.id) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = PrimaryDark
                    )
                )
            }
        }
    }
}
