package com.example.easterexplore.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.easterexplore.presentation.ui.screen.AddPOIScreen
import com.example.easterexplore.presentation.ui.screen.POIListScreen
import com.example.easterexplore.presentation.viewmodel.POIViewModel

@Composable
internal fun MainNavigation(
    poiViewModel: POIViewModel
) {
    val navController = rememberNavController()
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
                viewModel = poiViewModel
            )
        }
    }
}