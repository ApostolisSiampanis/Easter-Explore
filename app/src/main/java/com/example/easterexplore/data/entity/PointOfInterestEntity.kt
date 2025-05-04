package com.example.easterexplore.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.easterexplore.domain.model.Category
import com.example.easterexplore.domain.model.Location
import com.example.easterexplore.domain.model.PointOfInterest

/**
* Room Entity representing a Point of Interest record in the local database.
*
* @property id Auto-generated unique identifier for the POI.
* @property title Name/title of the point of interest.
* @property category Category enum (e.g., PARK, MUSEUM, etc.) for classifying the POI.
* @property rating Rating of the POI as a float value (e.g., from 0.0 to 10.0).
* @property location Embedded location object containing latitude and longitude.
* @property imageUri String path or URI pointing to an image associated with the POI.
* @property comments Additional descriptive text or notes about the POI.
*/
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
    /**
     * Converts the PointOfInterestEntity to a domain model PointOfInterest for use in the app layer.
     *
     * @return The corresponding PointOfInterest domain model.
     */
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

/**
 * Converts a domain model PointOfInterest to a PointOfInterestEntity for use in the database.
 *
 * @return The corresponding PointOfInterestEntity.
 */
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