package com.example.easterexplore.domain.repository

import com.example.easterexplore.domain.model.PointOfInterest
import kotlinx.coroutines.flow.Flow

interface POIRepository {

    fun getAllPOIs(): Flow<List<PointOfInterest>>
    suspend fun insertPOI(pointOfInterest: PointOfInterest)
    suspend fun deletePOI(pointOfInterest: PointOfInterest)

}