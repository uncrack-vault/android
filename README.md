![banner_uncrack](https://user-images.githubusercontent.com/80090908/223813616-345451bc-c90f-4ac1-ae29-13ad1fe43c4b.png)


# UnCrack 🔒
UnCrack is a simple android application made using Kotlin, for managing your password.

## About this app 🎯
Unlock the power of secure organization with UnCrack! This innovative app securely stores all your login credentials and other important information, so you never have to worry about forgetting passwords or searching for lost information. Keep your digital life organized and protected with UnCrack.

## Purpose of this app 🤨
I see a lot of people are tried of memorizing all their passwords, me also sometimes forgot the login credentials. So, after thinking about this situations, this app is build, to help user's to store their passwords and other information in a secured and organized manner.

## Featues 🔏

- UnCrack securely stores all login credentials and important information in one place, accessible only to the user. The app uses advanced encryption techniques to ensure that the data is protected from unauthorized access.

- With just a fingerprint, users can easily access all of their login information and never have to worry about forgetting passwords or searching for lost information.

- The app includes a secure password generator that can suggest stronger passwords and help users create unique and secure passwords for all their accounts.

- The app has a simple and user-friendly interface that makes it easy for users to manage their login information and passwords.


## Made With 🛠

- [Kotlin](https://developer.android.com/kotlin/first) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - For asynchronous and more..
- [Android Architecture Components](https://developer.android.com/topic/architecture) - Collection of libraries that help you design testable, and maintainable apps.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Room](https://developer.android.com/training/data-storage/room) - Room is an android library which is an ORM which wraps android's native SQLite database
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - LiveData was used to save and store values for viewModel calls and response of method calls.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Material Components for Android (MDC-Android) help developers execute Material Design. Developed by a core team of engineers and UX designers at Google, these components enable a reliable development workflow to build beautiful and functional Android apps.
- [Password Strength Meter](https://github.com/gustavaa/AndroidPasswordStrengthMeter) - Password strength meter is an easy-to-implement and flexible password strength indicator for Android. It is fully customizable and features an animated strength indicator and a matching label.

# Package Structure 👀

    com.geekymusketeers.uncrack    # Root Package
    
    ├── data                # For data handling.
    |   ├── model           # Model data classes, for local entities.
    │   ├── repository      # Single source of data.
    │   └── room            # For saving data.
    |
    ├── adapter             # All Adapters for recyclerViews              
    │   
    |
    ├── ui                  # UI/View layer
    |   ├── fragments       # All fragments     
    │   └── splashScreen    # SplashScreen
    |
    ├── utils               # Utility Classes / Kotlin extensions
    |
    └── viewmodel           # Generates a binding class for each XML layout file

## Architecture 👷‍♂️

This app uses [MVVM(Model View View-Model)](https://developer.android.com/topic/architecture#recommended-app-arch) architecture.

![Architecture_Flow](https://user-images.githubusercontent.com/80090908/216841302-97243bc3-3df4-4416-8f1f-dc22398c86b1.png)

## Support 💁‍♂️ & Download 💹

<a href="https://www.producthunt.com/posts/uncrack?utm_source=badge-featured&utm_medium=badge&utm_souce=badge-uncrack" target="_blank"><img src="https://api.producthunt.com/widgets/embed-image/v1/featured.svg?post_id=381745&theme=light" alt="UnCrack - 🔏&#0032;UnCrack&#0032;help&#0032;you&#0032;to&#0032;manage&#0032;&#0038;&#0032;secure&#0032;your&#0032;password&#0046; | Product Hunt" style="width: 250px; height: 54px;" width="250" height="54" /></a>  <a href="https://play.google.com/store/apps/details?id=com.geekymusketeers.uncrack"><img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png" height=84px /></a>

## Contact 📞
If you need any help, you can connect with me [here](https://www.linkedin.com/in/aritra-das-/).
