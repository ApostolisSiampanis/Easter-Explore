package com.example.easterexplore.domain.model

import androidx.annotation.StringRes
import com.example.easterexplore.R

enum class Category(@StringRes val displayNameRes: Int) {
    CHURCH(R.string.church),
    MUSEUM(R.string.museum),
    PARK(R.string.park),
    LANDMARK(R.string.landmark),
    VIEWPOINT(R.string.viewpoint),
    RESTAURANT(R.string.restaurant);
}