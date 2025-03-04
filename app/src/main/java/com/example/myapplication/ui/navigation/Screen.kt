package com.example.myapplication.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DataEntry : Screen("data_entry")
    object DataList : Screen("data_list")
    object Profile : Screen("profile")
    object EditData : Screen("edit")
}
