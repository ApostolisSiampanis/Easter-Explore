package com.example.easterexplore.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.easterexplore.domain.model.Category
import com.example.easterexplore.domain.model.Location
import com.example.easterexplore.domain.model.PointOfInterest

@Entity(tableName = "point_of_interest")
data class PointOfInterestEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "category")
    val category: Category,
    @ColumnInfo(name = "rating")
    val rating: Float,
    @Embedded
    val location: LocationEntity,
    @ColumnInfo(name = "image_uri")
    val imageUri: String,
    @ColumnInfo(name = "comments")
    val comments: String
) {
    fun toDomain() = PointOfInterest(
        id,
        title,
        category,
        rating,
        Location(
            location.latitude,
            location.longitude
        ),
        imageUri,
        comments
    )
}

fun PointOfInterest.toEntity() = PointOfInterestEntity(
    id,
    title,
    category,
    rating,
    LocationEntity(
        location.latitude,
        location.longitude
    ),
    imageUri,
    comments
)