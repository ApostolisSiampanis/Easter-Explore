package com.example.easterexplore.presentation.event

sealed class POIEvent {
    data object SavedSuccessfully : POIEvent()
    data class ShowError(val message: String) : POIEvent()
}