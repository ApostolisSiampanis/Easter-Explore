package com.example.easterexplore.di

import com.example.easterexplore.domain.repository.POIRepository
import com.example.easterexplore.domain.useCase.AddPOIUseCase
import com.example.easterexplore.domain.useCase.DeletePOIUseCase
import com.example.easterexplore.domain.useCase.GetPOIsUseCase

interface AppModule {

    // Repositories
    val poiRepository: POIRepository

    // Use Cases
    val getPOIsUseCase: GetPOIsUseCase
    val addPOIUseCase: AddPOIUseCase
    val deletePOIUseCase: DeletePOIUseCase

}