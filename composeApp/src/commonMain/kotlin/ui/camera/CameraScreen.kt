package ui.camera

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

object CameraScreen : Screen {

    @Composable
    override fun Content() {
        QrCodeScanner()
    }

}

@Composable
expect fun QrCodeScanner()