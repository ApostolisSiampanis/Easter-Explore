package com.example.easterexplore.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.easterexplore.presentation.utils.viewModelFactory
import com.example.easterexplore.EasterExplore
import com.example.easterexplore.presentation.ui.navigation.MainNavigation
import com.example.easterexplore.presentation.viewmodel.POIViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val poiViewModel = viewModel<POIViewModel>(
                    factory = viewModelFactory {
                        POIViewModel(
                            getPOIsUseCase = EasterExplore.appModule.getPOIsUseCase,
                            addPOIUseCase = EasterExplore.appModule.addPOIUseCase,
                            deletePOIUseCase = EasterExplore.appModule.deletePOIUseCase
                        )
                    }
                )

                MainNavigation(
                    poiViewModel = poiViewModel
                )
            }
        }
    }
}