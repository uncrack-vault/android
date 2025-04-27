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
  <a href="https://play.google.com/store/apps/details?id=com.aritradas.uncrack">
      <img src="https://img.shields.io/endpoint?color=purple&logo=google-play&style=for-the-badge&label=Play%20store&url=https%3A%2F%2Fplay.cuzi.workers.dev%2Fplay%3Fi%3Dcom.aritradas.uncrack%26l%3DAndroid%26m%3D%24version"/>
  </a>
  </br>
</p>

<h4 align="center">🔑UnCrack is a simple Android application made using Kotlin, for managing your password. UnCrack securely stores all your login credentials and other important information, 
  so you never have to worry about forgetting passwords or searching for lost information. Keep your digital life organized and protected with UnCrack.

  <br>
  <br>
  Please go through the <a href="https://github.com/uncrack-manager/android/blob/master/CONTRIBUTING.md">CONTRIBUTING.md</a> file before you start contributing.
</h4>

<div align="center">
  
# ⬇️ Download
Go to the [Releases](https://github.com/aritra-tech/UnCrack/releases/latest) and download latest apk
or click badges below.

<a href="https://play.google.com/store/apps/details?id=com.aritradas.uncrack"><img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png" height=90px />
<a href="https://github.com/aritra-tech/UnCrack/releases/latest"><img alt="Get it on GitHub" src="https://user-images.githubusercontent.com/69304392/148696068-0cfea65d-b18f-4685-82b5-329a330b1c0d.png" height=90px />

</div>

# 🔏 Featues 

- **Secure Vault for Passwords:** UnCrack allows users to securely store and manage their passwords, with access protected by encryption and possibly biometric authentication (fingerprint/face recognition).

- **Data Encryption:** All sensitive data, such as passwords and notes, are encrypted using industry-standard encryption methods (like AES-256) to prevent unauthorized access.

- **Password Generator:** The app includes a built-in password generator to help users create strong, unique passwords for improved security.

- **Password Health Checker:** A built-in password health checker that can help you identify which passwords are weak and strong and you can change them according to it.

- **Auto-Lock Mechanism:** The app automatically locks after a certain period of inactivity, requiring the user to re-authenticate.

## Installation steps

1. Clone the repository
   
   ```bash
    git clone https://github.com/uncrack-manager/android.git
    ```
    
2. This project requires a `google-services.json` file in:
- `app/src/debug/`
- `app/src/release/`

  To get your own config:
  1. Go to [Firebase Console](https://console.firebase.google.com/)
  2. Create a project and register your app (`com.example.yourapp`)
  3. Download the `google-services.json` file and place it in the appropriate folders


# ✨ Design 

<div align="center">
  <div>
    <img src="https://github.com/user-attachments/assets/2852daf8-04bc-450d-ad1e-7cd5991c6935" alt="Splash Screen" width="180"/>
    <img src="https://github.com/user-attachments/assets/5d100e52-9fca-4c8f-ab15-ff755ccc6e2d" alt="Vault Screen" width="180"/>
    <img src="https://github.com/user-attachments/assets/2e9fc2c4-d156-40f2-b406-91c07761b2e7" alt="Add Password Screen" width="180"/>
    <img src="https://github.com/user-attachments/assets/17c5a986-44e2-4e4e-89ff-f3d271893963" alt="Add Account Screen" width="180"/>
  </div>
  <div style="margin-top: 10px;">
    <img src="https://github.com/user-attachments/assets/5f66fb8b-1aeb-44ed-9fb8-3deba2e0f259" alt="Tools Screen" width="180"/>
    <img src="https://github.com/user-attachments/assets/0eaaf477-3c5a-4b30-b043-dd475a9b93b7" alt="Password Health Screen" width="180"/>
    <img src="https://github.com/user-attachments/assets/e8fa9ccc-8a08-4221-8ffa-b5e76379ee80" alt="Password Generator Screen" width="180"/>
    <img src="https://github.com/user-attachments/assets/a55d01cc-7c7f-4eb7-a510-406e74f34e24" alt="Profile Screen" width="180"/>
  </div>
</div>


# 🛠 Made With 

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous calls and tasks to utilize threads.
- [Jetpack Compose UI Toolkit](https://developer.android.com/jetpack/compose) - Modern UI development toolkit.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - Room is an Android library which is an ORM that wraps Android's native SQLite database.
  - [StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#:~:text=StateFlow%20is%20a%20state%2Dholder,property%20of%20the%20MutableStateFlow%20class.) - StateFlow and SharedFlow are Flow APIs that enable flows to optimally emit state updates and emit values to multiple consumers.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) -
    - [Hilt-Dagger](https://dagger.dev/hilt/) - A standard way to incorporate Dagger dependency injection into an Android application.
    - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting ```ViewModel```. 
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Firebase FireStore](https://firebase.google.com/docs/firestore) - Use our flexible, scalable NoSQL cloud database, built on Google Cloud infrastructure, to store and sync data for client- and server-side development.
- [Firebase Crashlytics](https://firebase.google.com/docs/crashlytics?hl=en&authuser=0) - Firebase Crashlytics is a lightweight, real-time crash reporter that helps you track crashes.
- [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging?hl=en&authuser=0) - Firebase Cloud Messaging (FCM) is a cross-platform messaging solution that lets you reliably send messages.

# 👷‍♂️ Architecture 

This app uses [MVVM(Model View View-Model)](https://developer.android.com/topic/architecture#recommended-app-arch) architecture.

![Architecture_Flow](https://user-images.githubusercontent.com/80090908/216841302-97243bc3-3df4-4416-8f1f-dc22398c86b1.png)


## Contact 📞
If you need any help, you can connect with me [here](https://www.linkedin.com/in/aritra-das-/).
