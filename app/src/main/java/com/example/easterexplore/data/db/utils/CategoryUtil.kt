package com.example.easterexplore.data.db.utils

import androidx.room.TypeConverter
import com.example.easterexplore.domain.model.Category

/**
 * Utility class to convert Category enum to String and vice versa for Room database.
 */
class CategoryUtil {

    /**
     * Converts a Category enum to its String representation.
     *
     * @param category The Category enum to convert.
     * @return The String representation of the Category.
     */
    @TypeConverter
    fun fromCategory(category: Category): String = category.name

    /**
     * Converts a String representation of a Category back to its enum.
     *
     * @param name The String representation of the Category.
     * @return The corresponding Category enum.
     */
    @TypeConverter
    fun toCategory(name: String): Category = Category.valueOf(name)

}