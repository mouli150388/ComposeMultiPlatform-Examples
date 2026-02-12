import SwiftUI
import FirebaseCore

@main
struct iOSApp: App {
    init() {
           FirebaseApp.configure() // Initialize for iOS
       }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
