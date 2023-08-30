<div align="center">
</br>
<img src="art/github_logo.svg" width="200" />

</div>

<h1 align="center">UnCrack</h1>

</br>
<p align="center">
  <img alt="API" src="https://img.shields.io/badge/Api%2021+-50f270?logo=android&logoColor=black&style=for-the-badge"/></a>
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-a503fc?logo=kotlin&logoColor=white&style=for-the-badge"/></a>
  <a href="https://github.com/aritra-tech/UnCrack/actions">
      <img alt="Build" src="https://img.shields.io/github/actions/workflow/status/aritra-tech/uncrack/ci_build.yml?label=Build&style=for-the-badge"/></a>
  <a href="https://github.com/aritra-tech/UnCrack/stargazers">
      <img src="https://img.shields.io/github/stars/aritra-tech/UnCrack?color=ffff00&style=for-the-badge"/>
  </a>
  <a href="https://hits.sh/github.com/aritra-tech/UnCrack/">
      <img alt="Hits" src="https://hits.sh/github.com/aritra-tech/UnCrack.svg?style=for-the-badge&label=Views&extraCount=7500&color=ff3f6f"/></a>
  <a href="https://github.com/aritra-tech/UnCrack/releases">
      <img src="https://img.shields.io/github/downloads/aritra-tech/uncrack/total?color=orange&style=for-the-badge"/>
  </a>
  <img alt="GitHub code size in bytes" src="https://img.shields.io/github/languages/code-size/aritra-tech/UnCrack?style=for-the-badge">
  <a href="">
      <img src="https://img.shields.io/github/v/release/aritra-tech/uncrack?color=purple&include_prereleases&logo=github&style=for-the-badge"/>
  </a>
  <a href="https://play.google.com/store/apps/details?id=com.geekymusketeers.uncrack">
      <img src="https://img.shields.io/endpoint?color=purple&logo=google-play&style=for-the-badge&label=Play%20store&url=https%3A%2F%2Fplay.cuzi.workers.dev%2Fplay%3Fi%3Dcom.geekymusketeers.uncrack%26l%3DAndroid%26m%3D%24version"/>
  </a>
  </br>
</p>

<h4 align="center">🔑UnCrack is a simple Android application made using Kotlin, for managing your password. UnCrack securely stores all your login credentials and other important information, 
  so you never have to worry about forgetting passwords or searching for lost information. Keep your digital life organized and protected with UnCrack.</h4>
  
<div align="center">
  
# ⬇️ Download
Go to the [Releases](https://github.com/aritra-tech/UnCrack/releases/latest) and download latest apk
or click badges below.

<a href="https://play.google.com/store/apps/details?id=com.geekymusketeers.uncrack"><img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png" height=90px />
<a href="https://github.com/aritra-tech/UnCrack/releases/latest"><img alt="Get it on GitHub" src="https://user-images.githubusercontent.com/69304392/148696068-0cfea65d-b18f-4685-82b5-329a330b1c0d.png" height=90px />

</div>

# 🤨 Purpose of this app
I see a lot of people are tried of memorizing all their passwords, me also sometimes forgot the login credentials. So, after thinking about this situations, this app is build, to help user's to store their passwords and other information in a secured and organized manner.

# 🔏 Featues 

- UnCrack securely stores all login credentials and important information in one place, accessible only to the user. The app uses advanced encryption techniques to ensure that the data is protected from unauthorized access.

- With just a fingerprint, users can easily access all of their login information and never have to worry about forgetting passwords or searching for lost information.

- The app includes a secure password generator that can suggest stronger passwords and help users create unique and secure passwords for all their accounts.

- The app has a simple and user-friendly interface that makes it easy for users to manage their login information and passwords.


# 🛠 Made With 

- [Kotlin](https://developer.android.com/kotlin/first) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - For asynchronous and more..
- [Android Architecture Components](https://developer.android.com/topic/architecture) - Collection of libraries that help you design testable, and maintainable apps.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Room](https://developer.android.com/training/data-storage/room) - Room is an android library which is an ORM which wraps android's native SQLite database
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - LiveData was used to save and store values for viewModel calls and response of method calls.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Password Strength Meter](https://github.com/gustavaa/AndroidPasswordStrengthMeter) - Password strength meter is an easy-to-implement and flexible password strength indicator for Android.
- [Firebase Crashlytics](https://firebase.google.com/docs/crashlytics?hl=en&authuser=0) - Firebase Crashlytics is a lightweight, real-time crash reporter that helps you track crashes.
- [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging?hl=en&authuser=0) - Firebase Cloud Messaging (FCM) is a cross-platform messaging solution that lets you reliably send messages.

# 👀 Package Structure 

    com.geekymusketeers.uncrack    # Root Package
    
    ├── data                # For data handling.
    |   ├── model           # Model data classes, for local entities.
    │   ├── repository      # Single source of data.
    │   └── room            # For saving data.
    |
    ├── adapter             # All Adapters for recyclerViews              
    │ 
    |── service             # Notification Service
    |
    ├── ui                  # UI/View layer
    |   ├── auth            # For Security
    |   └── fragments       # All fragments     
    │   └── splashScreen    # SplashScreen
    |
    ├── utils               # Utility Classes / Kotlin extensions
    |
    └── viewmodel           # Generates a binding class for each XML layout file

# 👷‍♂️ Architecture 

This app uses [MVVM(Model View View-Model)](https://developer.android.com/topic/architecture#recommended-app-arch) architecture.

![Architecture_Flow](https://user-images.githubusercontent.com/80090908/216841302-97243bc3-3df4-4416-8f1f-dc22398c86b1.png)


## Contact 📞
If you need any help, you can connect with me [here](https://www.linkedin.com/in/aritra-das-/).
