package com.example.easterexplore.domain.useCase

import com.example.easterexplore.domain.model.PointOfInterest
import com.example.easterexplore.domain.repository.POIRepository

/**
 * Use case for adding a new Point of Interest (POI) to the repository.
 *
 * @property repository The POI repository where the POI will be added.
 */
class AddPOIUseCase(private val repository: POIRepository) {

    /**
     * Invokes the use case to add a new POI.
     *
     * @param pointOfInterest The PointOfInterest object to be added.
     */
    suspend operator fun invoke(pointOfInterest: PointOfInterest) = repository.insertPOI(pointOfInterest)

}