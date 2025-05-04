package com.example.easterexplore.data.repository

import com.example.easterexplore.data.dao.PointOfInterestDao
import com.example.easterexplore.data.entity.toEntity
import com.example.easterexplore.domain.model.PointOfInterest
import com.example.easterexplore.domain.repository.POIRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of the POIRepository interface for managing Point of Interest data.
 *
 * @property dao The DAO for accessing Point of Interest data in the database.
 */
class POIRepositoryImpl(private val dao:PointOfInterestDao) : POIRepository {

    /**
     * Retrieves all Point of Interest records from the database as a reactive stream.
     *
     * @return A Flow emitting a list of all points of interest stored in the database.
     */
    override fun getAllPOIs(): Flow<List<PointOfInterest>>
        = dao.getAll().map { list ->
            list.map { it.toDomain() }
        }

    /**
     * Inserts a new point of interest into the database.
     *
     * @param pointOfInterest The PointOfInterest to be inserted.
     */
    override suspend fun insertPOI(pointOfInterest: PointOfInterest)
        = dao.insert(pointOfInterest.toEntity())

    /**
     * Deletes a specific point of interest from the database.
     *
     * @param pointOfInterest The PointOfInterest to be deleted.
     */
    override suspend fun deletePOI(pointOfInterest: PointOfInterest)
        = dao.delete(pointOfInterest.toEntity())

}