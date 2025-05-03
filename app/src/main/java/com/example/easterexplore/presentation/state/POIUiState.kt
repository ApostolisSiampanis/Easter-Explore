package com.example.easterexplore.presentation.state

import com.example.easterexplore.domain.model.PointOfInterest

data class POIUiState(
    val isLoading: Boolean = false,
    val pois: List<PointOfInterest> = emptyList()
)
