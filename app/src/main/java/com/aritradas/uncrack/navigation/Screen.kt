package com.aritradas.uncrack.navigation

sealed class Screen(val name: String) {

    data object BrowseScreen : Screen("home_screen")
    data object VaultScreen : Screen("vault_screen")
    data object ToolsScreen : Screen("tools_screen")
    data object SettingsScreen : Screen("settings_screen")
    data object AccountSelectionScreen : Screen("account_selection_screen")
    data object AddPasswordScreen : Screen("add_password_screen")
    data object EditPasswordScreen : Screen("edit_password_screen")
    data object ViewPasswordScreen : Screen("view_password_screen")
    data object ProfileScreen : Screen("profile_screen")
    data object HelpScreen : Screen("help_screen")
    data object UpdateMasterKeyScreen : Screen("update_master_key_screen")
    data object PasswordGeneratorScreen : Screen("password_generator_screen")
    data object PasswordHealthScreen : Screen("password_health_screen")
    data object CategoryScreen : Screen("category_screen")
}