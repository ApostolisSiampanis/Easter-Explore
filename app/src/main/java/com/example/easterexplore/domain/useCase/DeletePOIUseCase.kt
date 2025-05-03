package com.example.easterexplore.domain.useCase

import com.example.easterexplore.domain.model.PointOfInterest
import com.example.easterexplore.domain.repository.POIRepository

class DeletePOIUseCase(private val repository: POIRepository) {
    suspend operator fun invoke(pointOfInterest: PointOfInterest) = repository.deletePOI(pointOfInterest)
}