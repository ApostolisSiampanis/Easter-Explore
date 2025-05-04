package com.example.easterexplore.domain.useCase

import com.example.easterexplore.domain.model.PointOfInterest
import com.example.easterexplore.domain.repository.POIRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for retrieving all Point of Interest (POI) records from the repository.
 *
 * @property repository The POI repository from which to retrieve the POIs.
 */
class GetPOIsUseCase(private val repository: POIRepository) {

    /**
     * Invokes the use case to get all POIs.
     *
     * @return A Flow emitting a list of all points of interest stored in the database.
     */
    operator fun invoke(): Flow<List<PointOfInterest>> = repository.getAllPOIs()

}