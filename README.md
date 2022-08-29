Android Studio demo project by Alexander Krause-Glau (akr), Henning Schnoor (hs), and Sandro Esquivel (sae) for CAU Kiel Softwareprojekt.

# How to start
- Open in Android Studio with **Open** in the welcome dialog. Android Studio will download all dependencies (Gradle project), this might take a moment as indicated by the bottom right progress bar.

# How to run on virtual device
- Run with **Run -> Run app...** (Shift+F10)
- Create virtual device **Tablet Nexus 7** with **Android 11.0 (API 30)** if not present
- Select virtual device to run on
- (Optional) You might need to change the `BASE_URL` in the `BookModule` class.

# How to run on real device
- Activate developer mode on ASUS Nexus 7 (tap build number in **Setting -> About this device** 7 times)
- Connect ASUS Nexus 7 via USB (confirm to allow USB debugging)
- (Optional) Setup port forward for the Spring backend with `adb` command line tools, e.g., `adb reverse tcp:8080 tcp:8080`). The Android device can now reach the Spring-based backend on `localhost:8080`, you can test that via your mobile browser.
- (Optional) You might need to change the `BASE_URL` in the `BookModule` class.
- Run with **Run -> Run app...** (Shift+F10)
- Select device to run on

# Employed technologies
- IDE: [Android Studio](https://developer.android.com/studio)
- Dependency Injection: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- Network: [Retrofit](https://square.github.io/retrofit/), [OkHttp](https://square.github.io/okhttp) and [Moshi](https://github.com/square/moshi)
- Database: [Room](https://developer.android.com/jetpack/androidx/releases/room) and [RxJava](https://developer.android.com/training/data-storage/room/async-queries) for asynchronous database queries (do not block Main UI thread)

# Employed concepts
- [Single Activity Architecture](https://developer.android.com/guide/navigation/navigation-migrate) with [Navigation Component](https://developer.android.com/guide/navigation)
- [Repository Pattern](https://developer.android.com/topic/architecture/data-layer)
- [LiveData and ViewModels](https://developer.android.com/topic/libraries/architecture/livedata)

# How to reset local database?
- Uninstall app and redeploy via Android Studio.

# What is missing?
- Testing

# Guides
- [Meet Android Studio](https://developer.android.com/studio/intro)
- [Android Developer Guides](https://developer.android.com/guide)
- [Android API Reference](https://developer.android.com/reference/packages)
- [User Interface & Navigation](https://developer.android.com/guide/topics/ui)