package com.example.easterexplore.data.repository

import com.example.easterexplore.data.dao.PointOfInterestDao
import com.example.easterexplore.data.entity.toEntity
import com.example.easterexplore.domain.model.PointOfInterest
import com.example.easterexplore.domain.repository.POIRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class POIRepositoryImpl(private val dao:PointOfInterestDao) : POIRepository {

    override fun getAllPOIs(): Flow<List<PointOfInterest>>
        = dao.getAll().map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun insertPOI(pointOfInterest: PointOfInterest)
        = dao.insert(pointOfInterest.toEntity())

    override suspend fun deletePOI(pointOfInterest: PointOfInterest)
        = dao.delete(pointOfInterest.toEntity())

}