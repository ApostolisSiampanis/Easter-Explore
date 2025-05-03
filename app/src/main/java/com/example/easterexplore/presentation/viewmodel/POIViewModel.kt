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

class POIViewModel(
    private val getPOIsUseCase: GetPOIsUseCase,
    private val addPOIUseCase: AddPOIUseCase,
    private val deletePOIUseCase: DeletePOIUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(POIUiState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<POIEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            getPOIsUseCase().collect { pois ->
                _state.update { it.copy(pois = pois, isLoading = false) }
            }
        }
    }

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