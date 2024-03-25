package ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import challengeapp.composeapp.generated.resources.Res
import challengeapp.composeapp.generated.resources.back
import getPlatform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PlatformBackArrow() {
    val platform = getPlatform()
    val imageVector = when (platform.platformType) {
        PlatformType.ANDROID -> Icons.AutoMirrored.Default.ArrowBack
        PlatformType.IOS -> Icons.AutoMirrored.Default.ArrowBackIos
    }

    Icon(imageVector, contentDescription = stringResource(Res.string.back))
}
