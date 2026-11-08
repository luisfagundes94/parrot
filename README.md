# Parrot

Parrot is an Android dictionary app for English words. Search for a word, read its definitions,
parts of speech and example sentences, listen to its pronunciation with text-to-speech, and revisit
the words you looked up recently.

The app is written in Kotlin with Jetpack Compose and follows Clean Architecture with MVI for its
screens.

## Features

- **Search**: type a word and get suggestions as you type, backed by a sorted in-memory index.
- **Word detail**: meanings grouped by part of speech, example sentences, and text-to-speech
  playback of the word (US English).
- **Recently looked-up words**: the search screen lists the last words you opened. Only the 5 most
  recent are kept.
- **Offline-first lookups**: a word you have already searched is served from a local Room cache, so
  it works without a network connection. New words are fetched from
  the [Free Dictionary API](https://dictionaryapi.dev/).

Saved and Review are present in the bottom navigation, but their screens are still placeholders.

## Tech stack

| Area                 | Libraries                                              |
|----------------------|--------------------------------------------------------|
| Language             | Kotlin 2.4, Coroutines and Flow                        |
| UI                   | Jetpack Compose, Material 3, Navigation 3              |
| Architecture         | Clean Architecture (Presentation / Domain / Data), MVI |
| Dependency injection | Hilt (with KSP)                                        |
| Networking           | Retrofit, OkHttp, kotlinx.serialization                |
| Persistence          | Room                                                   |
| Testing              | JUnit 4, MockK, Turbine, Compose UI test               |

Requirements: Android Studio with support for Android Gradle Plugin 9.x, and a JDK that Gradle can
provision. The build targets Android API 37 and supports devices from Android 7.0 (API 24).

## Project structure

```text
parrot/
├── app/                      Entry point: MainActivity, top-level navigation, bottom bar
├── core/
│   ├── common/               MVI base classes, coroutine dispatchers, safeRunCatching
│   ├── navigation/           Navigator and navigation state shared across features
│   ├── designsystem/         Compose theme: colors, typography, spacing
│   └── dictionary/           Dictionary domain model, repository, Retrofit API, Room cache
└── feature/
    ├── search/{api,impl}     Search screen, autocomplete, recently looked-up words
    ├── word/{api,impl}       Word detail screen and text-to-speech
    ├── saved/{api,impl}      Saved words (placeholder screen)
    └── review/{api,impl}     Review (placeholder screen)
```

Each feature is split into an `api` module (navigation keys the rest of the app can reference) and
an `impl` module (screens, view models, and data sources). `impl` depends on `api`; `api` never
depends on `impl`.

Inside a module, code is organized by layer: `presentation` (screens, view models, state, events and
effects), `domain` (models and repository interfaces), and `data` (repository implementations,
network and database sources).

## Getting started

1. Clone the repository and open it in Android Studio.
2. Let Gradle sync. The build uses a Gradle toolchain resolver, so it can download a matching JDK if
   you do not have one.
3. Run the `app` configuration on an emulator or device.

The app calls `https://api.dictionaryapi.dev/` for words it has not seen before, so the device needs
internet access for the first lookup of each word. No API key is required.

## Building and testing

Run a focused unit test class:

```bash
./gradlew :core:dictionary:testDebugUnitTest --tests "com.luisfagundes.core.dictionary.data.repository.DictionaryRepositoryImplTest" --no-daemon -q
```

Run the checks for one module:

```bash
./gradlew :core:dictionary:check --no-daemon -q
```

Build a debug APK:

```bash
./gradlew :app:assembleDebug --no-daemon -q
```

Replace the module and test class names to target other parts of the code. The full `./gradlew test`
and `./gradlew check` tasks run the whole project and are slower, so prefer module-level runs while
developing.

## Contributing

Contributor and agent guidelines live in [`AGENTS.md`](AGENTS.md). In short:

- Keep Presentation depending on Domain, Data implementing Domain interfaces, and Domain free of
  Android and Data code.
- New screens use Compose and Material 3 tokens, with no new XML layouts.
- Use Kotlin coroutines and Flow. Do not call `runBlocking` in production code.
- Use Hilt for dependency injection.
- Add or update tests for behavior you change, including failure paths.
- Use [Conventional Commits](https://www.conventionalcommits.org/) for commit messages, for example
  `feature(search): ...` or `test(dictionary): ...`.
