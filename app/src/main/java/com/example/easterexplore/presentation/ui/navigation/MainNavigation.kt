package com.example.easterexplore.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.easterexplore.presentation.ui.screen.AddPOIScreen
import com.example.easterexplore.presentation.ui.screen.POIListScreen
import com.example.easterexplore.presentation.viewmodel.POIViewModel

/**
 * Main navigation function for the application.
 *
 * This function sets up the navigation graph for the app, defining the different screens and their
 * corresponding composable functions. It uses Jetpack Compose's Navigation component to handle
 * navigation between screens.
 *
 * @param poiViewModel The ViewModel instance used for managing points of interest (POI) data.
 */
@Composable
internal fun MainNavigation(
    poiViewModel: POIViewModel
) {
    // Initialize the navigation controller
    val navController = rememberNavController()

    // Define the navigation graph with NavHost
    NavHost(
        navController = navController,
        startDestination = MainDestination.POIList.route
    ) {
        composable(MainDestination.POIList.route) {
            POIListScreen(
                onAddClick = { navController.navigate(MainDestination.AddPOI.route) },
                viewModel = poiViewModel
            )
        }
        composable(MainDestination.AddPOI.route) {
            AddPOIScreen(
                onSave = { navController.popBackStack() },
                onBackPressed = { navController.popBackStack() },
                viewModel = poiViewModel
            )
        }
    }
}