# Parrot
A word reminder android app!

## Screenshots (Dark Theme)
<p float="left">
  <img src="https://github.com/luisfagundes94/parrot/blob/master/screenshots/screenshot1.jpg?raw=true" width="200" /> 
  <img src="https://github.com/luisfagundes94/parrot/blob/master/screenshots/screenshot2.jpg?raw=true" width="200" /> 
  <img src="https://github.com/luisfagundes94/parrot/blob/master/screenshots/screenshot3.jpg?raw=true" width="200" />
  <img src="https://github.com/luisfagundes94/parrot/blob/master/screenshots/screenshot4.jpg?raw=true" width="200" />
</p>

## Screenshots (Light Theme)
<p float="left">
  <img src="https://github.com/luisfagundes94/parrot/blob/master/screenshots/screenshotLight1.jpg?raw=true" width="200" />
  <img src="https://github.com/luisfagundes94/parrot/blob/master/screenshots/screenshotLight2.jpg?raw=true" width="200" />
  <img src="https://github.com/luisfagundes94/parrot/blob/master/screenshots/screenshotLight3.jpg?raw=true" width="200" />
  <img src="https://github.com/luisfagundes94/parrot/blob/master/screenshots/screenshotLight4.jpg?raw=true" width="200" />
</p> 

## Architecture
Architecture that is meticulously planned is paramount for an application's scalability, and all architectural designs share a universal aim - to handle the complexity of your application. This might not be a concern for smaller-scale apps, but it can be incredibly advantageous when working on apps with an extended development timeline and a bigger team.

Robert C. Martin introduced the concept of Clean Architecture in 2012 via the Clean Code Blog, and it abides by the SOLID principle.

<img src="https://miro.medium.com/v2/resize:fit:772/1*wOmAHDN_zKZJns9YDjtrMw.jpeg" width="500" />

## Layers

### Project Structure
<p align="start"><img src="https://github.com/luisfagundes94/parrot/blob/master/screenshots/projectStructure.png?raw=true" alt="Project Structure" width="500"></p>

### Commons
The ```commons``` layer is responsible for common properties. It contains the implementations of the resources files, themes and components declared in the common layer.

- __testing__: This is responsible for common testing rules.
- __ui__: Has common UI components across the app.
- __theme__: Defines themes, colors, fonts and resource files.
- __util__: Mainly for storing global constants.

### Data
The ```data``` layer is responsible for selecting the proper data source for the domain layer. It contains the implementations of the repositories declared in the domain layer.

### Domain
This is the core layer of the application. The ```domain``` layer is independent of any other layers thus ] domain business logic can be independent from other layers.This means that changes in other layers will have no effect on domain layer eg.  screen UI (presentation layer) or changing database (data layer) will not result in any code change withing domain layer.

### Presentation
The ```features``` layer contains components involved in showing information to the user. The main part of this layer are the views(activity, compose) and ViewModels.

### Framework
The ```framework``` layer is responsible for core functionalities, such as model for network responses, extensions, and base classes.

### Plugins
The ```plugins``` layer is responsible for dependency management. It control and manage all dependencies in one place with Kotlin using the Gradle Version Catalog.

# Tech Stacks
This project uses many of the popular libraries, plugins and tools of the android ecosystem.

- [Compose](https://developer.android.com/jetpack/compose)

    - [Material3](https://developer.android.com/jetpack/androidx/releases/compose-material3) - Build Jetpack Compose UIs with ready to use Material Design Components.
    - [Foundation](https://developer.android.com/jetpack/androidx/releases/compose-foundation) - Write Jetpack Compose applications with ready to use building blocks and extend foundation to build your own design system pieces.
    - [UI](https://developer.android.com/jetpack/androidx/releases/compose-ui) - Fundamental components of compose UI needed to interact with the device, including layout, drawing, and input.
    - [Lifecycle-ViewModel](https://developer.android.com/jetpack/androidx/releases/lifecycle) - Perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.
    - [HiltViewModel](https://dagger.dev/hilt/view-model.html) - Hilt provides a standard way to incorporate Dagger dependency injection into an Android application.
    - [Lottie](https://github.com/airbnb/lottie/blob/master/android-compose.md) - Lottie is a mobile library for Android and iOS that parses Adobe After Effects animations exported as json with Bodymovin and renders them natively on mobile!

- [Accompanist](https://google.github.io/accompanist)

    - [SwipeRefresh](https://google.github.io/accompanist/swiperefresh/) - A library which provides a layout which provides the swipe-to-refresh UX pattern, similar to Android's SwipeRefreshLayout.
    - [Systemuicontroller](https://google.github.io/accompanist/systemuicontroller/) - System UI Controller provides easy-to-use utilities for updating the System UI bar colors within Jetpack Compose.
    - [Insets](https://google.github.io/accompanist/insets/) - Insets for Jetpack Compose takes a lot of the ideas which drove Insetter for views, and applies them for use in composables.
    - [Placeholder](https://google.github.io/accompanist/placeholder/) - A library which provides a modifier for display 'placeholder' UI while content is loading.
    - [Navigation](https://google.github.io/accompanist/navigation-material/) - A library which provides Compose Material support for Jetpack Navigation Compose. This features composable bottom sheet destinations.

- [Jetpack](https://developer.android.com/jetpack)

    - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers.
    - [Android KTX](https://developer.android.com/kotlin/ktx.html) - Provide concise, idiomatic Kotlin to Jetpack and Android platform APIs.
    - [AndroidX](https://developer.android.com/jetpack/androidx) - Major improvement to the original Android [Support Library](https://developer.android.com/topic/libraries/support-library/index), which is no longer maintained.
    - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.
    - [Room](https://developer.android.com/training/data-storage/room) - Provides an abstraction layer over SQLite used for offline data caching.


- [Dagger Hilt](https://dagger.dev/hilt/) - Dependency Injection library.
- [Kapt](https://kotlinlang.org/docs/kapt.html) - Kapt compiler plugin
- [Retrofit](https://square.github.io/retrofit/) - Type-safe http client and supports coroutines out of the box.
- [OkHttp-Logging-Interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - Logs HTTP request and response data.
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines.
- [Flow](https://developer.android.com/kotlin/flow) - Flows are built on top of coroutines and can provide multiple values. A flow is conceptually a stream of data that can be computed asynchronously.
- [Material Design 3](https://m3.material.io/develop/android/mdc-android) - Build awesome beautiful UIs.
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines,provides runBlocking coroutine builder used in tests.
- [Timber](https://github.com/JakeWharton/timber) - A logger with a small, extensible API which provides utility on top of Android's normal Log class.
- [Gradle Kotlin DSL](https://gradle.org/kotlin/) - makes it easy to manage dependency all module that we have

- [SplashScreen](https://developer.android.com/guide/topics/ui/splash-screen) - Android 12 adds the SplashScreen API, which enables a new app launch animation for all apps when running on a device with Android 12 or higher.

- [Test](https://en.wikipedia.org/wiki/Unit_testing)
    - [JUnit4](https://junit.org/junit4/) - JUnit is a simple framework to write repeatable tests.
    - [Mockk](https://mockk.io/) - A modern Mockk library for UnitTest.
    - [Coroutine-Test](https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-test) - Provides testing utilities for effectively testing coroutines.

### Plugin
- [Check-Dependency-Versions](https://github.com/ben-manes/gradle-versions-plugin) - make easy to determine which dependencies have updates.
- [GradleVersionCatalog](https://docs.gradle.org/current/userguide/platforms.html) - Gradle's support for declaring and using dependencies.

### Code Analyze Tools
- [Ktlint](https://github.com/jlleitschuh/ktlint-gradle) - A ktlint gradle plugin. Provides a convenient wrapper plugin over the ktlint project.
- [Spotless](https://github.com/diffplug/spotless) - It’s pretty useful in automating fixes for pretty simple (and common) formatting mistakes as in spaces, newlines, removing unnecessary imports, etc.
