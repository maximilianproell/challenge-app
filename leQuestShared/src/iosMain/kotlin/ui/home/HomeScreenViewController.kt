package ui.home
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import ui.theme.LeQuestAppTheme
import ui.utils.koinViewModel

fun HomeScreenViewController(
    onCompleteChallengeClick: () -> Unit,
    onProfileClick: () -> Unit,
): UIViewController = ComposeUIViewController {
    LeQuestAppTheme {
        val viewModel = koinViewModel<HomeScreenViewModel>()

        HomeScreenContent(
            viewModel = viewModel,
            onCompleteClick = onCompleteChallengeClick,
            onProfileClick = onProfileClick,
            onGetMeThereClick = {}
        )
    }
}