package com.example.easterexplore.presentation.ui.navigation

sealed class MainDestination(val route: String) {
    data object POIList : MainDestination("list")
    data object AddPOI : MainDestination("add")
}