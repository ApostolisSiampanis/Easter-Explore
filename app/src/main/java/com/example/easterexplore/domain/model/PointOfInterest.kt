package com.example.easterexplore.domain.model

/**
 * Data class representing a Point of Interest in the application for the domain layer.
 *
 * @property id The unique identifier for the point of interest.
 * @property title The name or title of the point of interest.
 * @property category The category of the point of interest (e.g., PARK, MUSEUM).
 * @property rating The rating of the point of interest, typically a float value.
 * @property location The geographical location of the point of interest, represented by latitude and longitude.
 * @property imageUri The URI or path to an image associated with the point of interest.
 * @property comments Additional comments or notes about the point of interest.
 */
data class PointOfInterest(
    val id: Int = 0,
    val title: String,
    val category: Category,
    val rating: Float,
    val location: Location,
    val imageUri: String,
    val comments: String,
)
