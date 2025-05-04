package com.example.easterexplore.di

import com.example.easterexplore.domain.repository.POIRepository
import com.example.easterexplore.domain.useCase.AddPOIUseCase
import com.example.easterexplore.domain.useCase.DeletePOIUseCase
import com.example.easterexplore.domain.useCase.GetPOIsUseCase

/**
 * Dependency Injection interface that provides access to application-wide dependencies.
 * This abstraction allows you to define how various components (like repositories and use cases)
 * are provided throughout the app, promoting testability and separation of concerns.
 */

interface AppModule {

    // Repository that handles data operations for Points of Interest
    val poiRepository: POIRepository

    // Use case to retrieve all Points of Interest
    val getPOIsUseCase: GetPOIsUseCase

    // Use cases for add a new Point of Interest
    val addPOIUseCase: AddPOIUseCase

    // Use case to delete a Point of Interest
    val deletePOIUseCase: DeletePOIUseCase

}