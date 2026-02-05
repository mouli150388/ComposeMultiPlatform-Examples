This is a Kotlin Multiplatform project targeting Android, iOS.

## Phone Call Implementation

This project implements a cross-platform way to trigger phone calls using Compose Multiplatform.

### Implementation Details

1.  **Common Interface (`commonMain`)**: Defines the `PhoneCaller` interface and an `expect` function `rememberPhoneCaller()`.
2.  **Android Implementation (`androidMain`)**: Uses `Intent.ACTION_DIAL` with the `tel:` URI.
3.  **iOS Implementation (`iosMain`)**: Uses `UIApplication.sharedApplication.openURL` with the `tel:` scheme.

### Usage Snippet

```kotlin
@Composable
fun App() {
    val phoneCaller = rememberPhoneCaller()
    
    Button(onClick = {
        phoneCaller.makeCall("1234567890")
    }) {
        Text("Call Now")
    }
}
```

---

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…