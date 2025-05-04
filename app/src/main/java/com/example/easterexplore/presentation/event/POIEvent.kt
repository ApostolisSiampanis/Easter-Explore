package com.example.easterexplore.presentation.event

/**
 * Sealed class representing events related to Point of Interest (POI) operations.
 *
 * This class is used to handle different events that can occur in the POI feature,
 * such as saving a POI successfully or showing an error message.
 */
sealed class POIEvent {

    /**
     * Event indicating that a POI has been saved successfully.
     */
    data object SavedSuccessfully : POIEvent()

    /**
     * Event indicating that an error occurred while processing a POI.
     *
     * @param message The error message to be displayed.
     */
    data class ShowError(val message: String) : POIEvent()

}