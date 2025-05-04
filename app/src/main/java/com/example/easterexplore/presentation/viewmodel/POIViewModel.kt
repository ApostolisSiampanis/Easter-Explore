package com.example.easterexplore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easterexplore.domain.model.PointOfInterest
import com.example.easterexplore.domain.useCase.AddPOIUseCase
import com.example.easterexplore.domain.useCase.DeletePOIUseCase
import com.example.easterexplore.domain.useCase.GetPOIsUseCase
import com.example.easterexplore.presentation.event.POIEvent
import com.example.easterexplore.presentation.state.POIUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for managing the state and events related to Points of Interest (POIs).
 *
 * @param getPOIsUseCase Use case for fetching POIs.
 * @param addPOIUseCase Use case for adding a new POI.
 * @param deletePOIUseCase Use case for deleting a POI.
 */
class POIViewModel(
    private val getPOIsUseCase: GetPOIsUseCase,
    private val addPOIUseCase: AddPOIUseCase,
    private val deletePOIUseCase: DeletePOIUseCase
) : ViewModel() {

    // StateFlow to hold the UI state
    private val _state = MutableStateFlow(POIUiState())
    val state = _state.asStateFlow()

    // SharedFlow to emit events
    private val _event = MutableSharedFlow<POIEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            getPOIsUseCase().collect { pois ->
                _state.update { it.copy(pois = pois, isLoading = false) }
            }
        }
    }

    /**
     * Adds a new Point of Interest (POI).
     *
     * @param pointOfInterest The POI to be added.
     */
    fun add(pointOfInterest: PointOfInterest) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }
                addPOIUseCase(pointOfInterest)
                _event.emit(POIEvent.SavedSuccessfully)
            } catch (e: Exception) {
                _event.emit(POIEvent.ShowError("Save failed"))
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    /**
     * Deletes a Point of Interest (POI).
     *
     * @param pointOfInterest The POI to be deleted.
     */
    fun delete(pointOfInterest: PointOfInterest) {
        viewModelScope.launch {
            try {
                deletePOIUseCase(pointOfInterest)
            } catch (e: Exception) {
                _event.emit(POIEvent.ShowError("Delete failed"))
            }
        }
    }

}