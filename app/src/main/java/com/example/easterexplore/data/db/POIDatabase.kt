package com.example.easterexplore.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.easterexplore.data.dao.PointOfInterestDao
import com.example.easterexplore.data.db.utils.CategoryUtil
import com.example.easterexplore.data.entity.PointOfInterestEntity

@Database(entities = [PointOfInterestEntity::class], version = 1)
@TypeConverters(CategoryUtil::class)
abstract class POIDatabase : RoomDatabase() {

    abstract fun pointOfInterestDao(): PointOfInterestDao

    companion object {
        @Volatile
        private var INSTANCE: POIDatabase? = null

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