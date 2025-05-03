package com.example.easterexplore.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.easterexplore.data.entity.PointOfInterestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PointOfInterestDao {

    @Query("SELECT * FROM point_of_interest")
    fun getAll(): Flow<List<PointOfInterestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pointOfInterest: PointOfInterestEntity)

    @Delete
    suspend fun delete(pointOfInterest: PointOfInterestEntity)

}