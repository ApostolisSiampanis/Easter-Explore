package com.example.easterexplore.domain.model

data class PointOfInterest(
    val id: Int = 0,
    val title: String,
    val category: Category,
    val rating: Float,
    val location: Location,
    val imageUri: String,
    val comments: String,
)
