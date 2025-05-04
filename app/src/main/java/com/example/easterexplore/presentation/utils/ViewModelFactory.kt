package com.example.easterexplore.presentation.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * A utility function to create a ViewModel factory.
 *
 * @param initializer A lambda function that initializes the ViewModel.
 * @return A ViewModelProvider.Factory instance.
 */
fun <VM: ViewModel> viewModelFactory(initializer: () -> VM): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return initializer() as T
        }
    }
}