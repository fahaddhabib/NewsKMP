# NewsKMP 📰

A modern News application built with **Kotlin Multiplatform (KMP)** targeting Android and iOS. Fetches real-time top stories from the **New York Times API** with section-based browsing and article detail view.

## Features

- 📱 Runs on both **Android and iOS** from a single shared codebase
- 🗞️ Browse news by sections: Home, Technology, World, Business, Science, Arts, Politics, Sports
- 🖼️ Article thumbnails with image loading
- 📄 Article detail screen with full image and abstract
- 🔗 Open full article in browser
- ⚡ Section-based caching — no repeated API calls
- 🔄 Pull to retry on error
- 📭 Empty state handling per section

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| Architecture | MVVM |
| Shared Logic | Kotlin Multiplatform (KMP) |
| Networking | Ktor Client |
| Serialization | Kotlinx Serialization |
| Image Loading | Coil 3 |
| Dependency Injection | Koin |
| UI — Android | Jetpack Compose |
| UI — iOS | SwiftUI |
| State Management | StateFlow + ViewModel (AAC) |
| API | New York Times Top Stories API |

## Project Structure
NewsKMP/
├── sharedLogic/
│   └── commonMain/
│       └── data/
│           ├── model/
│           ├── remote/
│           ├── repository/
│           ├── di/
│           └── utils/
├── sharedUI/
│   └── commonMain/
│       ├── di/
│       ├── NewsListScreen.kt
│       ├── ArticleDetailScreen.kt
│       ├── NewsViewModel.kt
│       ├── AppViewModel.kt
│       └── App.kt
├── androidApp/
│   ├── MainActivity.kt
│   └── NewsApplication.kt
└── iosApp/
└── ContentView.swift

## Architecture
UI Layer (Compose / SwiftUI)
↓
ViewModel (StateFlow + AAC ViewModel)
↓
Repository (NewsRepositoryImpl)
↓
ApiService (ApiServiceImpl + Ktor Client)
↓
NYT Top Stories API

## Dependency Injection

This project uses **Koin** for dependency injection on Android.

| Module | Contents |
|---|---|
| `networkModule` | `ApiServiceImpl` bound to `ApiService` |
| `repositoryModule` | `NewsRepositoryImpl` bound to `NewsRepository` |
| `viewModelModule` | `NewsViewModel`, `AppViewModel` |

Koin is initialized in `NewsApplication.kt` and all modules are registered at app startup. iOS uses manual DI via SwiftUI's native state management.

## Getting Started

### Prerequisites

- Android Studio (latest)
- Xcode 15+ (for iOS)
- JDK 11+

### Step 1 — Get a NYT API Key

1. Go to [https://developer.nytimes.com](https://developer.nytimes.com)
2. Click **Sign In** → **Create Account**
3. After login go to **My Apps** → **+ New App**
4. Give your app a name
5. Enable **Top Stories API**
6. Click **Create** — your API key will be shown

### Step 2 — Clone the Repository

```bash
git clone https://github.com/fahaddhabib/NewsKMP.git
cd NewsKMP
```

### Step 3 — Add Your API Key

Open `local.properties` in the project root and add:

```properties
NYT_API_KEY=your_api_key_here
```

> ⚠️ Never commit `local.properties` to GitHub — it is already in `.gitignore`

### Step 4 — Run on Android

Open project in **Android Studio**, select `androidApp` run configuration and click **Run**, or use:

```bash
./gradlew :androidApp:assembleDebug
```

### Step 5 — Run on iOS

Build the shared framework first:

```bash
./gradlew :sharedLogic:linkDebugFrameworkIosSimulatorArm64
```

Then open in Xcode:

```bash
open iosApp/iosApp.xcodeproj
```

Select a simulator and click **Run**.

## Module Details

This is a Kotlin Multiplatform project targeting Android and iOS.

- **[/iosApp](./iosApp/iosApp)** — iOS application entry point with SwiftUI code.
- **[/sharedLogic](./sharedLogic/src)** — Shared business logic. [commonMain](./sharedLogic/src/commonMain/kotlin) contains networking, models, repository logic and Koin DI modules.
- **[/sharedUI](./sharedUI/src)** — Shared Compose UI. [commonMain](./sharedUI/src/commonMain/kotlin) contains common UI for all targets and ViewModel DI modules.

## Running Tests

```bash
# Android tests
./gradlew :sharedUI:testAndroidHostTest :sharedLogic:testAndroidHostTest

# iOS tests
./gradlew :sharedLogic:iosSimulatorArm64Test
```

## Platforms

| Platform | Status |
|---|---|
| Android | ✅ Supported |
| iOS | ✅ Supported |

## License

MIT License — see [LICENSE](./LICENSE) file for details.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
