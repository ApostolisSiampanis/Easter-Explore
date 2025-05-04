package com.example.easterexplore.di

import android.content.Context
import com.example.easterexplore.data.db.POIDatabase
import com.example.easterexplore.data.repository.POIRepositoryImpl
import com.example.easterexplore.domain.repository.POIRepository
import com.example.easterexplore.domain.useCase.AddPOIUseCase
import com.example.easterexplore.domain.useCase.DeletePOIUseCase
import com.example.easterexplore.domain.useCase.GetPOIsUseCase

/**
 * Implementation of the AppModule interface, responsible for providing application-wide dependencies.
 * This acts as a manual Dependency Injection container.
 *
 * @param appContext The application context, used to initialize the database.
 */

class AppModuleImpl(private val appContext: Context): AppModule {

    // Room Database instance, created using the application context.
    private val database: POIDatabase = POIDatabase.getDatabase(appContext)

    // Repository instance for accessing Point of Interest data, lazily initialized to avoid unnecessary loading.
    override val poiRepository: POIRepository by lazy {
        POIRepositoryImpl(database.pointOfInterestDao())
    }

    // Use case for retrieving all Points of Interest.
    override val getPOIsUseCase: GetPOIsUseCase by lazy {
        GetPOIsUseCase(poiRepository)
    }

    // Use case for adding a new Point of Interest.
    override val addPOIUseCase: AddPOIUseCase by lazy {
        AddPOIUseCase(poiRepository)
    }

    // Use case for deleting a Point of Interest.
    override val deletePOIUseCase: DeletePOIUseCase by lazy {
        DeletePOIUseCase(poiRepository)
    }

}