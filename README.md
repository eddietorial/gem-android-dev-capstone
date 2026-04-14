# Little Lemon Food Ordering App 

Author: Geoffrey Edmund Moraes

GitHub: https://github.com/eddietorial/gem-android-dev-capstone

Course: Android App Capstone (Coursera)

An Android food ordering app for the Little Lemon Mediterranean restaurant, built with Jetpack Compose. This is the capstone project for the Meta Android Developer Professional Certificate on Coursera.

---

## Features

- Onboarding screen that collects first name, last name, and email on first launch
- Persistent login: user data is retained across app restarts via SharedPreferences
- Home screen with a hero banner, search bar, and category filter chips
- Food menu loaded from a remote API and cached locally in a Room database
- Profile screen displaying the registered user's details
- Log out clears all stored data and returns to the Onboarding screen
- Stack navigation with correct back-button behavior throughout

---

## Tech Stack

| Layer | Technology |
|---|---|
| UI | Jetpack Compose, Material3 |
| Navigation | Jetpack Navigation Compose |
| Networking | Ktor (Android client, ContentNegotiation, kotlinx.serialization) |
| Local storage | Room (Entity, DAO, KSP annotation processor) |
| Image loading | Glide Compose |
| State | LiveData + observeAsState, remember + mutableStateOf |
| Persistence | SharedPreferences |

---

## Requirements

- Android Studio with AGP 8.8.0 support (tested on Hedgehog or newer)
- JDK 11 or higher
- Android device or emulator running API 21 or higher
- Internet connection on first launch (to fetch the menu from the remote API)

---

## Building from Source

### 1. Clone the repository

```bash
git clone https://github.com/eddietorial/gem-android-dev-capstone.git LittleLemon
cd LittleLemon
```

### 2. Open in Android Studio

Go to `File > Open` and select the cloned `LittleLemon` folder. Wait for the Gradle sync to complete before proceeding.

### 3. Run the app

Connect a device or start an emulator, then click `Run > Run 'app'` or press `Shift+F10`.

On first launch the app shows the Onboarding screen. Enter your name and email to register. The menu loads automatically from the network and is cached for offline use.

---

## Project Structure

```
app/src/main/java/com/yourname/littlelemon/
    MainActivity.kt       - Entry point; Ktor fetch and Room insert on launch
    Navigation.kt         - NavHost; login-state-aware start destination
    Destinations.kt       - Typed route objects (Onboarding, Home, Profile)
    Network.kt            - Ktor JSON models and entity mapper
    Database.kt           - Room Entity, DAO, and singleton AppDatabase
    Onboarding.kt         - Registration screen with input validation
    Home.kt               - Hero section, search, category filter, menu list
    Profile.kt            - User data display and logout
    ui/theme/
        Color.kt          - Brand palette (Primary1, Primary2, Highlight1/2)
        Type.kt           - Markazi Text + Karla typography
        Theme.kt          - LittleLemonTheme composable
```

---

## API

Menu data is fetched from:

```
https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json
```

The response is deserialized into `MenuNetwork` / `MenuItemNetwork` data classes and persisted to a local Room database. The UI observes the database via `LiveData`, so the list updates automatically once the fetch completes.

---

## Known Limitations

- User registration data is stored in plaintext SharedPreferences. This is appropriate for a course project but not for production.
- The app does not support placing actual orders; menu display is read-only.

---

## License

This project is submitted as coursework for the Meta Android Developer Professional Certificate. The Little Lemon brand assets and API data are provided by Meta for educational use.
