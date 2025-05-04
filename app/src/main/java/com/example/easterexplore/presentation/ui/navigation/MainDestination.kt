package com.example.easterexplore.presentation.ui.navigation

/**
 * Sealed class representing the main destinations in the application.
 *
 * This class is used to define the different screens or destinations that can be navigated to
 * within the app, such as the list of points of interest (POI) and the screen for adding a new POI.
 *
 * @property route The unique route string associated with each destination.
 */
sealed class MainDestination(val route: String) {
    data object POIList : MainDestination("list")
    data object AddPOI : MainDestination("add")
}