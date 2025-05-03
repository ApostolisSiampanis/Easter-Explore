package com.example.easterexplore.domain.useCase

import com.example.easterexplore.domain.model.PointOfInterest
import com.example.easterexplore.domain.repository.POIRepository
import kotlinx.coroutines.flow.Flow

class GetPOIsUseCase(private val repository: POIRepository) {
    operator fun invoke(): Flow<List<PointOfInterest>> = repository.getAllPOIs()
}