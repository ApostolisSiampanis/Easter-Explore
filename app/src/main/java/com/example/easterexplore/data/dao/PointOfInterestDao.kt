package com.example.easterexplore.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.easterexplore.data.entity.PointOfInterestEntity
import kotlinx.coroutines.flow.Flow

/**
* Data Access Object (DAO) interface for managing Point of Interest data in the Room database.
 */
@Dao
interface PointOfInterestDao {

    /**
     * Retrieves all PointOfInterestEntity records from the database as a reactive stream.
     *
     * @return A Flow emitting a list of all points of interest stored in the database.
     */
    @Query("SELECT * FROM point_of_interest")
    fun getAll(): Flow<List<PointOfInterestEntity>>

    /**
     * Inserts a new point of interest into the database.
     * If a point of interest with the same ID already exists, it will be replaced.
     *
     * @param pointOfInterest The PointOfInterestEntity to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pointOfInterest: PointOfInterestEntity)

    /**
     * Deletes a specific point of interest from the database.
     *
     * @param pointOfInterest The PointOfInterestEntity to be deleted.
     */
    @Delete
    suspend fun delete(pointOfInterest: PointOfInterestEntity)

}