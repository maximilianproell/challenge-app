package ui.home
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import ui.theme.LeQuestAppTheme

fun HomeScreenViewController(
    onCompleteChallengeClick: () -> Unit,
    onProfileClick: () -> Unit,
): UIViewController = ComposeUIViewController {
    LeQuestAppTheme {
        HomeScreenContent(
            onCompleteClick = onCompleteChallengeClick,
            onProfileClick = onProfileClick,
            onGetMeThereClick = {}
        )
    }
}