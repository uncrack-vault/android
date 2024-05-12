package com.geekymusketeers.uncrack.navigation

sealed class Screen(val name: String) {

    data object BrowseScreen : Screen("home_screen")
    data object VaultScreen : Screen("vault_screen")
    data object ToolsScreen : Screen("shield_screen")
    data object AccountScreen : Screen("account_screen")
    data object AccountSelectionScreen : Screen("account_selection_screen")
    data object AddPasswordScreen : Screen("add_password_screen")
    data object EditPasswordScreen : Screen("edit_password_screen")
    data object ViewPasswordScreen : Screen("view_password_screen")
    data object ProfileScreen : Screen("profile_screen")
    data object UpdateMasterKeyScreen : Screen("update_master_key_screen")
    data object ConfirmMasterKeyScreen : Screen("confirm_master_key_screen")
    data object PasswordGeneratorScreen : Screen("password_generator_screen")
    data object PasswordHealthScreen : Screen("password_health_screen")
    data object CategoryScreen : Screen("category_screen")
}