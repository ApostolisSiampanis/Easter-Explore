package com.example.easterexplore.data.db.utils

import androidx.room.TypeConverter
import com.example.easterexplore.domain.model.Category

class CategoryUtil {

    @TypeConverter
    fun fromCategory(category: Category): String = category.name

    @TypeConverter
    fun toCategory(name: String): Category = Category.valueOf(name)

}