package com.example.easterexplore.presentation.state

import com.example.easterexplore.domain.model.PointOfInterest

/**
 * UI state for the Point of Interest (POI) screen.
 *
 * @property isLoading Indicates whether the data is currently being loaded.
 * @property pois A list of PointOfInterest objects representing the POIs.
 */
data class POIUiState(

    /**
     * Indicates whether the data is currently being loaded.
     */
    val isLoading: Boolean = false,

    /**
     * A list of PointOfInterest objects representing the POIs.
     */
    val pois: List<PointOfInterest> = emptyList()

)
