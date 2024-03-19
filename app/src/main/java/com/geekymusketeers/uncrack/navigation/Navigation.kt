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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.presentation.account.AccountScreen
import com.geekymusketeers.uncrack.presentation.account.PasswordGenerator
import com.geekymusketeers.uncrack.presentation.account.PasswordGeneratorViewModel
import com.geekymusketeers.uncrack.presentation.category.CategoryScreen
import com.geekymusketeers.uncrack.presentation.home.HomeScreen
import com.geekymusketeers.uncrack.presentation.masterKey.ConfirmMasterKeyScreen
import com.geekymusketeers.uncrack.presentation.masterKey.CreateMasterKeyScreen
import com.geekymusketeers.uncrack.presentation.masterKey.UpdateMasterKey
import com.geekymusketeers.uncrack.presentation.profile.ProfileScreen
import com.geekymusketeers.uncrack.presentation.shield.ShieldScreen
import com.geekymusketeers.uncrack.presentation.vault.VaultScreen
import com.geekymusketeers.uncrack.sharedViewModel.ThemeViewModel
import com.geekymusketeers.uncrack.ui.theme.BackgroundLight
import com.geekymusketeers.uncrack.ui.theme.DMSansFontFamily
import com.geekymusketeers.uncrack.ui.theme.FadeIn
import com.geekymusketeers.uncrack.ui.theme.FadeOut
import com.geekymusketeers.uncrack.ui.theme.OnPrimaryContainerLight
import com.geekymusketeers.uncrack.ui.theme.OnSurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.PrimaryDark
import com.geekymusketeers.uncrack.util.BackPressHandler
import com.geekymusketeers.uncrack.viewModel.KeyViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    masterKeyViewModel: KeyViewModel = hiltViewModel(),
    passwordGeneratorViewModel: PasswordGeneratorViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()

    val screensWithoutNavigationBar = persistentListOf(
        "add_password_screen",
        "profile_screen",
        "update_master_key_screen",
        "create_new_master_key_screen",
        "confirm_master_key_screen",
        "password_generator_screen",
        "category_screen"
    )

    BackPressHandler()

    Scaffold(
        modifier = modifier,
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
                HomeScreen(
                    navController
                )
            }

            composable(route = "vault_screen") {
                VaultScreen()
            }

            composable(route = "shield_screen") {
                ShieldScreen()
            }

            composable(route = "profile_screen") {
                ProfileScreen()
            }

            composable(route = "account_screen") {
                AccountScreen(
                    navController,
                    themeViewModel
                )
            }

            composable(route = "update_master_key_screen") {
                UpdateMasterKey(
                    navController,
                    masterKeyViewModel
                )
            }

            composable(route = "create_new_master_key_screen") {
                CreateMasterKeyScreen(
                    navController,
                    masterKeyViewModel
                )
            }

            composable(route = "confirm_master_key_screen") {
                ConfirmMasterKeyScreen(
                    navController,
                    masterKeyViewModel
                )
            }

            composable(route = "password_generator_screen") {
                PasswordGenerator(
                    navController,
                    passwordGeneratorViewModel
                )
            }

            composable(route = "category_screen") {
                CategoryScreen(
                    navController
                )
            }
        }
    }
}

@Composable
fun ShowBottomNavigation(
    backStackEntry: State<NavBackStackEntry?>,
    screensWithoutNavigationBar: ImmutableList<String>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    if (backStackEntry.value?.destination?.route !in screensWithoutNavigationBar) {
        NavigationBar(
            modifier = modifier,
            containerColor = BackgroundLight
        ) {

            val bottomNavItems = listOf(
                BottomNavItem(
                    name = "Home",
                    route = "home_screen",
                    icon = ImageVector.vectorResource(id = R.drawable.home_icon)
                ),
                BottomNavItem(
                    name = "Vault",
                    route = "vault_screen",
                    icon = ImageVector.vectorResource(id = R.drawable.password)
                ),
                BottomNavItem(
                    name = "Shield",
                    route = "shield_screen",
                    icon = ImageVector.vectorResource(id = R.drawable.shield)
                ),
                BottomNavItem(
                    name = "Profile",
                    route = "account_screen",
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
                                OnPrimaryContainerLight
                            else
                                OnSurfaceVariantLight
                        )
                    },
                    label = {
                        Text(
                            text = item.name,
                            fontFamily = DMSansFontFamily,
                            color = if (backStackEntry.value?.destination?.route == item.route)
                                OnPrimaryContainerLight
                            else
                                OnSurfaceVariantLight,
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
