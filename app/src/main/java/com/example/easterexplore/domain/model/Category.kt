package com.example.easterexplore.domain.model

enum class Category(val displayName: String) {
    CHURCH("Church"),
    MUSEUM("Museum"),
    PARK("Park"),
    LANDMARK("Landmark"),
    VIEWPOINT("Viewpoint"),
    RESTAURANT("Restaurant");

    override fun toString() = displayName
}