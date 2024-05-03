package ui.profile

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun ProfileScreenViewController(): UIViewController = ComposeUIViewController {
    ProfileScreen.Content()
}