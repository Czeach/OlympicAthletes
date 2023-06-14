# OlympicAthletes

displays a list of athletes, according to games, (from an API) in a List and shows the details of
each athlete in a detail view. The athletes are ordered in descending order for each game according
to their points

## Tech stack and Libraries used

* Minimum SDK level: 24
* Kotlin, 100% Jetpack Compose, Coroutines and Flow for asynchronous programming
* Jetpack libraries
    * Jetpack Compose for UI
    * Navigation Compose for Navigate between composables while leveraging of the Navigation
      component’s infrastructure and features
    * ViewModel to Encapsulate related business logic and manage UI data in a lifecycle-aware
      fashion
    * Hilt for dependency injection
    * Retrofit2 as REST client
* Architecture: This app follows Google's official architecture guidance. It is based on the MVVM
  architecture and the Repository pattern.
* JUnit and Mockk for unit and instrumented tests
* Fastlane with Firebase CLI for app automation

## Instructions to execute

This app can run on any android emulator or device with at least android version 7.0.

* on a terminal in the project's root folder
    * run ```fastlane build``` to build the project
    * run ```fastlane test``` to run unit tests
    * run ```fastlane beta``` to send a release build apk to your email address. (in
      /Users/zennymorh/Amarachi/Projects/OlympicAthletes/fastlane/Fastfile, add an email address to
      the "testers" parameter of firebase_app_distribution in the :beta lane )

