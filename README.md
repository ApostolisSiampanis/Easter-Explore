# Easter Explore

## Project Overview

Easter Explore is an Android application for saving and browsing points of interest (POIs) you visit. Users can add a place with a title, category, rating, comments and a photo, and the app records the place's location automatically using the device's GPS.

The app is built with Kotlin and Jetpack Compose, follows the Clean Architecture approach (data, domain and presentation layers) and stores all data locally with Room.

## Key Features

- **Add a Point of Interest:** Enter a title, pick a category, set a rating from 0 to 10 and add comments.
- **Categories:** Church, Museum, Park, Landmark, Viewpoint and Restaurant.
- **Photo Capture:** Take a photo with the device camera. The image is saved in the app's internal storage.
- **Automatic Location:** The current latitude and longitude are fetched with the Fused Location Provider when a place is saved.
- **POI List:** Browse all saved places with their category, rating, photo and coordinates.
- **Delete:** Remove a place from the list.
- **Runtime Permissions:** Camera and location permissions are requested only when needed, with clear messages if they are denied.

## Technologies Used

### Kotlin & Jetpack Compose

- **Jetpack Compose:** Declarative UI toolkit used for all screens.
- **Material 3:** Components and theming.
- **Navigation Compose:** Navigation between the POI list and the Add POI screen.

### Architecture

- **Clean Architecture:** Separate data, domain and presentation layers.
- **MVVM:** A `ViewModel` exposes UI state and one-time events to the Compose screens.
- **Use Cases:** `AddPOIUseCase`, `GetPOIsUseCase` and `DeletePOIUseCase` hold the business logic.
- **Manual Dependency Injection:** An `AppModule` provides the database, repository and use cases.

### Room Database

- Local persistence of points of interest, with a type converter for categories and an embedded location (latitude, longitude).

### Location Services

- **Google Play Services (Location):** Fused Location Provider for the device's location.

### Coil

- Image loading for POI photos in Compose.

## Project Structure

```
/app/src/main/java/com/example/easterexplore
    /data
        /dao              # Room DAO
        /db               # Room database and type converters
        /entity           # Database entities
        /repository       # Repository implementation
    /domain
        /model            # Domain models (PointOfInterest, Category, Location)
        /repository       # Repository interface
        /useCase          # Add, get and delete use cases
    /di                   # Manual dependency injection
    /presentation
        /event            # One-time UI events
        /state            # UI state
        /ui               # Screens, components, navigation and theme
        /utils            # Location, file and ViewModel helpers
        /viewmodel        # POIViewModel
```

## Requirements

- Android Studio
- An Android device or emulator running **Android 14 (API 34)** or later
- Camera and location permissions

## Setup Instructions

1. Clone the repository:

    ```bash
    git clone https://github.com/ApostolisSiampanis/Easter-Explore.git
    ```

2. Open the project in Android Studio.
3. Let Gradle sync the dependencies.
4. Build and run the app on an Android device or emulator.

## License

This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.
