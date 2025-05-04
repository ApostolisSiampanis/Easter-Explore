package com.example.easterexplore.domain.useCase

import com.example.easterexplore.domain.model.PointOfInterest
import com.example.easterexplore.domain.repository.POIRepository

/**
 * Use case for adding a new Point of Interest (POI) to the repository.
 *
 * @property repository The POI repository where the POI will be added.
 */
class DeletePOIUseCase(private val repository: POIRepository) {

    /**
     * Invokes the use case to delete a POI.
     *
     * @param pointOfInterest The PointOfInterest object to be deleted.
     */
    suspend operator fun invoke(pointOfInterest: PointOfInterest) = repository.deletePOI(pointOfInterest)

}