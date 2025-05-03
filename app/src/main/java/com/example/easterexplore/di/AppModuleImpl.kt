package com.example.easterexplore.di

import android.content.Context
import androidx.room.Room
import com.example.easterexplore.data.db.POIDatabase
import com.example.easterexplore.data.repository.POIRepositoryImpl
import com.example.easterexplore.domain.repository.POIRepository
import com.example.easterexplore.domain.useCase.AddPOIUseCase
import com.example.easterexplore.domain.useCase.DeletePOIUseCase
import com.example.easterexplore.domain.useCase.GetPOIsUseCase

class AppModuleImpl(private val appContext: Context): AppModule {

    // Database
    private val database: POIDatabase = POIDatabase.getDatabase(appContext)

    // Repository
    override val poiRepository: POIRepository by lazy {
        POIRepositoryImpl(database.pointOfInterestDao())
    }

    // Use cases
    override val getPOIsUseCase: GetPOIsUseCase by lazy {
        GetPOIsUseCase(poiRepository)
    }

    override val addPOIUseCase: AddPOIUseCase by lazy {
        AddPOIUseCase(poiRepository)
    }

    override val deletePOIUseCase: DeletePOIUseCase by lazy {
        DeletePOIUseCase(poiRepository)
    }

}