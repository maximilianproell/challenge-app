package ui.camera

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.interop.UIKitViewController
import androidx.compose.ui.window.ComposeUIViewController
import domain.model.Challenge
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIViewController


@Composable
actual fun QrCodeScanner() {
    // Here will be the iOS implementation.
    //QrCodeScannerEntryPointWithUIViewController()

}
