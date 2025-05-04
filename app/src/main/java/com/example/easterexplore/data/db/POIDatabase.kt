package com.example.easterexplore.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.easterexplore.data.dao.PointOfInterestDao
import com.example.easterexplore.data.db.utils.CategoryUtil
import com.example.easterexplore.data.entity.PointOfInterestEntity

/**
 * Room database class for managing Point of Interest data.
 *
 * @property pointOfInterestDao DAO for accessing Point of Interest data.
 */
@Database(entities = [PointOfInterestEntity::class], version = 1)
@TypeConverters(CategoryUtil::class)
abstract class POIDatabase : RoomDatabase() {

    /**
     * Abstract function to get the DAO for accessing Point of Interest data.
     */
    abstract fun pointOfInterestDao(): PointOfInterestDao

    companion object {
        // Volatile ensures that the instance is visible to all threads immediately.
        @Volatile
        private var INSTANCE: POIDatabase? = null

        /**
         * Returns the singleton instance of the POIDatabase.
         * Initializes the database if it doesn't exist.
         *
         * @param context The application context.
         * @return The singleton instance of POIDatabase.
         */
        fun getDatabase(context: Context): POIDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    POIDatabase::class.java,
                    "poi_database",
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}