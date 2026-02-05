# KMP Email Attachment

This project demonstrates how to implement cross-platform email sending functionality in a Compose Multiplatform application for Android and iOS.

## Features

- **Cross-Platform API**: A unified `EmailManager` interface used in `commonMain`.
- **Android Implementation**: Uses `Intent.ACTION_SENDTO` with the `mailto:` scheme to ensure only email apps handle the request.
- **iOS Implementation**: 
    - Uses `UIApplication.openURL` with `mailto:` scheme for native email clients.
    - **Simulator Support**: Includes a fallback to open a web-based mail client (Gmail) if no native email app is available (common on simulators).
    - **Modern UIKit API**: Uses the non-deprecated `openURL:options:completionHandler:` method.

## Implementation Details

### Common Module (`commonMain`)
The `EmailManager` is defined as an `expect class` with a `sendEmail` function. A factory function `getEmailManager()` is provided to obtain the platform-specific instance.

### Android (`androidMain`)
- Requires initialization in `MainActivity` using `initEmailManager(this)` to provide the necessary `Context`.
- Uses `Intent` flags to handle the activity stack correctly.

### iOS (`iosMain`)
- Implements `NSURLComponents` to safely build `mailto:` URLs with proper query encoding.
- Configured via `Info.plist` with `LSApplicationQueriesSchemes` to allow querying for `mailto`, `http`, and `https` schemes.

## Configuration

### iOS `Info.plist`
To allow the app to open external URLs, the following schemes are whitelisted:
```xml
<key>LSApplicationQueriesSchemes</key>
<array>
    <string>mailto</string>
    <string>https</string>
    <string>http</string>
</array>
```

## Usage

In your Compose code:
```kotlin
val emailManager = getEmailManager()
emailManager.sendEmail(
    recipient = "example@email.com",
    subject = "Hello from KMP",
    body = "This is a test email!"
)
```
