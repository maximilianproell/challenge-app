package ui.home
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import ui.theme.ChallengeAppTheme

fun HomeScreenViewController(
    onCompleteChallengeClick: () -> Unit,
    onProfileClick: () -> Unit,
): UIViewController = ComposeUIViewController {
    ChallengeAppTheme {
        HomeScreenContent(
            onCompleteClick = onCompleteChallengeClick,
            onProfileClick = onProfileClick,
            onGetMeThereClick = {}
        )
    }
}