package com.geekymusketeers.uncrack.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.presentation.account.AccountScreen
import com.geekymusketeers.uncrack.presentation.shield.PasswordGenerator
import com.geekymusketeers.uncrack.presentation.shield.viewModel.PasswordGeneratorViewModel
import com.geekymusketeers.uncrack.presentation.category.CategoryScreen
import com.geekymusketeers.uncrack.presentation.home.HomeScreen
import com.geekymusketeers.uncrack.presentation.home.HomeViewModel
import com.geekymusketeers.uncrack.presentation.masterKey.ConfirmMasterKeyScreen
import com.geekymusketeers.uncrack.presentation.masterKey.CreateMasterKeyScreen
import com.geekymusketeers.uncrack.presentation.masterKey.UpdateMasterKey
import com.geekymusketeers.uncrack.presentation.profile.ProfileScreen
import com.geekymusketeers.uncrack.presentation.shield.PasswordHealthScreen
import com.geekymusketeers.uncrack.presentation.shield.ShieldScreen
import com.geekymusketeers.uncrack.presentation.shield.viewModel.ShieldViewModel
import com.geekymusketeers.uncrack.presentation.vault.AccountSelectionScreen
import com.geekymusketeers.uncrack.presentation.vault.AddPasswordScreen
import com.geekymusketeers.uncrack.presentation.vault.EditPasswordScreen
import com.geekymusketeers.uncrack.presentation.vault.VaultScreen
import com.geekymusketeers.uncrack.presentation.vault.ViewPasswordScreen
import com.geekymusketeers.uncrack.presentation.vault.viewmodel.AddEditViewModel
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
import com.geekymusketeers.uncrack.presentation.vault.viewmodel.VaultViewModel
import com.geekymusketeers.uncrack.presentation.vault.viewmodel.ViewPasswordViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    masterKeyViewModel: KeyViewModel = hiltViewModel(),
    passwordGeneratorViewModel: PasswordGeneratorViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel(),
    vaultViewModel: VaultViewModel = hiltViewModel(),
    addEditViewModel: AddEditViewModel = hiltViewModel(),
    shieldViewModel: ShieldViewModel = hiltViewModel(),
    viewPasswordViewModel: ViewPasswordViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()

    val screensWithoutNavigationBar = persistentListOf(
        Screen.AccountSelectionScreen.name,
        "${Screen.AddPasswordScreen.name}?accountIcon={accountIcon}&accountName={accountName}&accountCategory={accountCategory}",
        "${Screen.EditPasswordScreen.name}/{accountID}",
        Screen.ProfileScreen.name,
        Screen.UpdateMasterKeyScreen.name,
        Screen.CreateMasterKeyScreen.name,
        Screen.ConfirmMasterKeyScreen.name,
        Screen.PasswordGeneratorScreen.name,
        Screen.CategoryScreen.name,
        "${Screen.ViewPasswordScreen.name}/{id}",
        Screen.PasswordHealthScreen.name
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
            startDestination = "vault_screen",
            enterTransition = { FadeIn },
            exitTransition = { FadeOut },
            popEnterTransition = { FadeIn },
            popExitTransition = { FadeOut }
        ) {

            composable(route = Screen.HomeScreen.name) {
                HomeScreen(
                    navController,
                    homeViewModel
                )
            }

            composable(route = Screen.VaultScreen.name) {
                VaultScreen(
                    onFabClicked = { navController.navigate(Screen.AccountSelectionScreen.name) },
                    vaultViewModel = vaultViewModel,
                    homeViewModel,
                    navigateToViewPasswordScreen = { id ->
                        navController.navigate("${Screen.ViewPasswordScreen.name}/$id")
                    }
                )
            }

            composable(route = Screen.AccountSelectionScreen.name) {
                AccountSelectionScreen(
                    navController
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

            composable(route = Screen.ShieldScreen.name) {
                ShieldScreen(navController,shieldViewModel)
            }

            composable(route = Screen.ProfileScreen.name) {
                ProfileScreen()
            }

            composable(route = Screen.AccountScreen.name) {
                AccountScreen(
                    navController,
                    themeViewModel,
                    homeViewModel
                )
            }

            composable(route = Screen.UpdateMasterKeyScreen.name) {
                UpdateMasterKey(
                    navController,
                    masterKeyViewModel
                )
            }

            composable(route = Screen.CreateMasterKeyScreen.name) {
                CreateMasterKeyScreen(
                    navController,
                    masterKeyViewModel
                )
            }

            composable(route = Screen.ConfirmMasterKeyScreen.name) {
                ConfirmMasterKeyScreen(
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
                PasswordHealthScreen(navController)
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
                BottomNavItem(
                    name = "Browse",
                    route = "home_screen",
                    icon = ImageVector.vectorResource(id = R.drawable.home_icon)
                ),
                BottomNavItem(
                    name = "Tools",
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
