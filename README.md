# Jarvis AI Assistant

🤖 **Standalone Native Android Kotlin Application**

## Overview

Jarvis is a production-ready Android AI Assistant with Jetpack Compose, Gemini API, and joke generator.

## Features

✨ **Current Implementation**
- 🔐 User Authentication with encrypted storage
- 😂 Random Joke Generator using Official Joke API
- 🌙 Dark/Light theme support with Material3
- 📱 Responsive UI for all Android versions (API 29+)

## Quick Start

1. Clone: `git clone https://github.com/KUSHAL7997/jarvis-ai-assistant.git`
2. Build: `./gradlew build`
3. Install: `./gradlew installDebug`
4. Launch Jarvis!

## API Integrations

### Official Joke API
- Fetch random jokes: `https://official-joke-api.appspot.com/jokes/random`
- Free, no authentication

### Google Gemini API
- Endpoint: `https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent`
- Get key: [Google AI Studio](https://aistudio.google.com/app/apikey)
- Optional during login (can use default key)

## Security

✅ AES-256 encrypted SharedPreferences
✅ No plaintext credentials
✅ ProGuard enabled for releases
✅ Runtime permission handling

## Project Structure

```
app/src/main/
├── kotlin/com/kushal/jarvis/
│   ├── JarvisApplication.kt
│   ├── ui/
│   │   ├── MainActivity.kt
│   │   ├── PermissionsScreen.kt
│   │   ├── theme/Theme.kt
│   │   ├── screen/
│   │   │   ├── LoginScreen.kt
│   │   │   └── HomeScreen.kt
│   │   └── viewmodel/MainViewModel.kt
│   ├── data/
│   │   ├── local/SharedPrefsManager.kt
│   │   └── remote/
│   │       ├── JokeApiService.kt
│   │       └── GeminiApiService.kt
│   ├── domain/usecase/
│   │   ├── JokeUseCase.kt
│   │   └── GeminiUseCase.kt
│   └── service/
│       ├── JarvisForegroundService.kt
│       ├── JarvisAccessibilityService.kt
│       └── JarvisNotificationListener.kt
└── res/
```

## Target Device

📱 Samsung Galaxy A21s
- Exynos 850 (8x Cortex-A55)
- Mali-G52 MP1 GPU
- 3GB/4GB RAM
- Android 11-13 (API 30-33)

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose + Material3
- **Architecture**: MVVM + Clean Architecture
- **Networking**: Retrofit + OkHttp
- **Security**: EncryptedSharedPreferences

## License

Apache License 2.0

## Contact

📧 kushalkushal08638@gmail.com
🔗 [@KUSHAL7997](https://github.com/KUSHAL7997)

---

**Built with ❤️ for Android Development**
