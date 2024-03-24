package com.geekymusketeers.uncrack.navigation

sealed class Screen(val name: String) {

    data object HomeScreen : Screen("home_screen")
    data object VaultScreen : Screen("vault_screen")
    data object ShieldScreen : Screen("shield_screen")
    data object AccountScreen : Screen("account_screen")
    data object AddEditPasswordScreen : Screen("add_edit_password_screen")
    data object ViewPasswordScreen : Screen("view_password_screen")
    data object ProfileScreen : Screen("profile_screen")
    data object UpdateMasterKeyScreen : Screen("update_master_key_screen")
    data object CreateMasterKeyScreen : Screen("create_new_master_key_screen")
    data object ConfirmMasterKeyScreen : Screen("confirm_master_key_screen")
    data object PasswordGeneratorScreen : Screen("password_generator_screen")
    data object CategoryScreen : Screen("category_screen")
}