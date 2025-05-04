package com.example.easterexplore.domain.repository

import com.example.easterexplore.domain.model.PointOfInterest
import kotlinx.coroutines.flow.Flow

/**
 * Interface for managing Point of Interest (POI) data.
 *
 * This interface defines methods for retrieving, inserting, and deleting POIs.
 */
interface POIRepository {

    /**
     * Retrieves all Point of Interest records from the database as a reactive stream.
     *
     * @return A Flow emitting a list of all points of interest stored in the database.
     */
    fun getAllPOIs(): Flow<List<PointOfInterest>>

    /**
     * Inserts a new point of interest into the database.
     *
     * @param pointOfInterest The PointOfInterest to be inserted.
     */
    suspend fun insertPOI(pointOfInterest: PointOfInterest)

    /**
     * Deletes a specific point of interest from the database.
     *
     * @param pointOfInterest The PointOfInterest to be deleted.
     */
    suspend fun deletePOI(pointOfInterest: PointOfInterest)

}