package com.aritradas.uncrack.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aritradas.uncrack.R
import com.aritradas.uncrack.presentation.browse.BrowseScreen
import com.aritradas.uncrack.presentation.browse.category.CategoryScreen
import com.aritradas.uncrack.presentation.masterKey.KeyViewModel
import com.aritradas.uncrack.presentation.masterKey.updateMasterKey.UpdateMasterKey
import com.aritradas.uncrack.presentation.profile.HelpScreen
import com.aritradas.uncrack.presentation.profile.ProfileScreen
import com.aritradas.uncrack.presentation.settings.SettingsScreen
import com.aritradas.uncrack.presentation.settings.SettingsViewModel
import com.aritradas.uncrack.presentation.tools.ToolsScreen
import com.aritradas.uncrack.presentation.tools.passwordGenerator.PasswordGenerator
import com.aritradas.uncrack.presentation.tools.passwordGenerator.PasswordGeneratorViewModel
import com.aritradas.uncrack.presentation.tools.passwordHealth.PassHealthViewModel
import com.aritradas.uncrack.presentation.tools.passwordHealth.PasswordHealthScreen
import com.aritradas.uncrack.presentation.vault.AccountSelectionScreen
import com.aritradas.uncrack.presentation.vault.AddPasswordScreen
import com.aritradas.uncrack.presentation.vault.EditPasswordScreen
import com.aritradas.uncrack.presentation.vault.VaultScreen
import com.aritradas.uncrack.presentation.vault.ViewPasswordScreen
import com.aritradas.uncrack.presentation.vault.viewmodel.AddEditViewModel
import com.aritradas.uncrack.presentation.vault.viewmodel.VaultViewModel
import com.aritradas.uncrack.presentation.vault.viewmodel.ViewPasswordViewModel
import com.aritradas.uncrack.sharedViewModel.ThemeViewModel
import com.aritradas.uncrack.sharedViewModel.UserViewModel
import com.aritradas.uncrack.ui.theme.BackgroundLight
import com.aritradas.uncrack.ui.theme.DMSansFontFamily
import com.aritradas.uncrack.ui.theme.FadeIn
import com.aritradas.uncrack.ui.theme.FadeOut
import com.aritradas.uncrack.ui.theme.OnPrimaryContainerLight
import com.aritradas.uncrack.ui.theme.OnSurfaceVariantLight
import com.aritradas.uncrack.ui.theme.PrimaryDark
import com.aritradas.uncrack.util.BackPressHandler
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(
    activity: Activity,
    modifier: Modifier = Modifier,
    masterKeyViewModel: KeyViewModel = hiltViewModel(),
    passwordGeneratorViewModel: PasswordGeneratorViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel(),
    vaultViewModel: VaultViewModel = hiltViewModel(),
    addEditViewModel: AddEditViewModel = hiltViewModel(),
    passwordHealthViewModel: PassHealthViewModel = hiltViewModel(),
    viewPasswordViewModel: ViewPasswordViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()

    val screensWithoutNavigationBar = persistentListOf(
        Screen.AccountSelectionScreen.name,
        "${Screen.AddPasswordScreen.name}?accountIcon={accountIcon}&accountName={accountName}&accountCategory={accountCategory}",
        "${Screen.EditPasswordScreen.name}/{accountID}",
        Screen.SettingsScreen.name,
        Screen.UpdateMasterKeyScreen.name,
        Screen.PasswordGeneratorScreen.name,
        Screen.CategoryScreen.name,
        "${Screen.ViewPasswordScreen.name}/{id}",
        Screen.PasswordHealthScreen.name,
        Screen.HelpScreen.name
    )

    BackPressHandler()

    Scaffold(
        modifier = Modifier.fillMaxSize().then(modifier),
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
            startDestination = "vault_screen",
            enterTransition = { FadeIn },
            exitTransition = { FadeOut },
            popEnterTransition = { FadeIn },
            popExitTransition = { FadeOut }
        ) {

            composable(route = Screen.BrowseScreen.name) {
                BrowseScreen(
                    navController
                )
            }

            composable(route = Screen.VaultScreen.name) {
                VaultScreen(
                    onFabClicked = { navController.navigate(Screen.AccountSelectionScreen.name) },
                    vaultViewModel = vaultViewModel,
                    userViewModel,
                    navigateToViewPasswordScreen = { id ->
                        navController.navigate("${Screen.ViewPasswordScreen.name}/$id")
                    }
                )
            }

            composable(route = Screen.AccountSelectionScreen.name) {
                AccountSelectionScreen(
                    navController,
                    addEditViewModel
                ) { accountIcon, accountName, accountCategory ->
                    navController.navigate("${Screen.AddPasswordScreen.name}?accountIcon=$accountIcon&accountName=$accountName&accountCategory=$accountCategory")
                }
            }

            composable(
                route = "${Screen.AddPasswordScreen.name}?accountIcon={accountIcon}&accountName={accountName}&accountCategory={accountCategory}",
                arguments = listOf(
                    navArgument("accountIcon") { type = IntType },
                    navArgument("accountName") { type = StringType },
                    navArgument("accountCategory") { type = StringType }
                )
            ) {

                val accountIconId = backStackEntry.value?.arguments?.getInt("accountIcon") ?: 0
                val accountTextId = backStackEntry.value?.arguments?.getString("accountName") ?: ""
                val accountCategoryId = backStackEntry.value?.arguments?.getString("accountCategory") ?: ""

                AddPasswordScreen(
                    navController,
                    accountIconId,
                    accountTextId,
                    accountCategoryId,
                    addEditViewModel
                )
            }

            composable(
                route = "${Screen.EditPasswordScreen.name}/{accountID}",
                arguments = listOf(navArgument("accountID") { type = IntType })
            ) { backStackEntry ->
                val accountId = backStackEntry.arguments?.getInt("accountID") ?: 0
                EditPasswordScreen(
                    navController,
                    accountId,
                    viewPasswordViewModel
                )
            }

            composable(
                route = "${Screen.ViewPasswordScreen.name}/{id}",
                arguments = listOf(navArgument("id") { type = IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                ViewPasswordScreen(
                    navController,
                    accountId = id,
                    viewPasswordViewModel,
                    navigateToEditPasswordScreen = { accountID ->
                        navController.navigate("${Screen.EditPasswordScreen.name}/$accountID")
                    }
                )
            }

            composable(route = Screen.ToolsScreen.name) {
                ToolsScreen(navController)
            }

            composable(route = Screen.ProfileScreen.name) {
                ProfileScreen(
                    navController,
                    userViewModel
                )
            }

            composable(route = Screen.HelpScreen.name) {
                HelpScreen(navController)
            }

            composable(route = Screen.SettingsScreen.name) {
                SettingsScreen(
                    activity,
                    navController,
                    settingsViewModel
                )
            }

            composable(route = Screen.UpdateMasterKeyScreen.name) {
                UpdateMasterKey(
                    navController,
                    masterKeyViewModel
                )
            }

            composable(route = Screen.PasswordGeneratorScreen.name) {
                PasswordGenerator(
                    navController,
                    passwordGeneratorViewModel
                )
            }

            composable(route = Screen.PasswordHealthScreen.name) {
                PasswordHealthScreen(navController, passwordHealthViewModel)
            }

            composable(route = Screen.CategoryScreen.name) {
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
                    name = "Vault",
                    route = "vault_screen",
                    icon = ImageVector.vectorResource(id = R.drawable.password)
                ),
//                BottomNavItem(
//                    name = "Browse",
//                    route = "home_screen",
//                    icon = ImageVector.vectorResource(id = R.drawable.browse)
//                ),
                BottomNavItem(
                    name = "Tools",
                    route = "tools_screen",
                    icon = ImageVector.vectorResource(id = R.drawable.tools)
                ),
                BottomNavItem(
                    name = "Profile",
                    route = "profile_screen",
                    icon = ImageVector.vectorResource(id = R.drawable.person_icon)
                )
            )

            bottomNavItems.forEach { item ->
                val isSelected = backStackEntry.value?.destination?.route == item.route
                val animateIconSize by animateFloatAsState(
                    if (isSelected) 1f else 0.9f,
                    label = "iconScale"
                )

                NavigationBarItem(
                    alwaysShowLabel = true,
                    icon = {
                        Icon(
                            modifier = Modifier.scale(animateIconSize),
                            imageVector = item.icon,
                            contentDescription = item.name,
                            tint = if (isSelected)
                                OnPrimaryContainerLight
                            else
                                OnSurfaceVariantLight
                        )
                    },
                    label = {
                        Text(
                            text = item.name,
                            fontFamily = DMSansFontFamily,
                            color = if (isSelected)
                                OnPrimaryContainerLight
                            else
                                OnSurfaceVariantLight,
                            fontWeight = if (isSelected)
                                FontWeight.SemiBold
                            else
                                FontWeight.Normal,
                        )
                    },
                    selected = isSelected,
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
