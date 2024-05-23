package ui.home
import androidx.compose.ui.window.ComposeUIViewController
import androidx.lifecycle.viewmodel.compose.viewModel
import platform.UIKit.UIViewController
import ui.theme.LeQuestAppTheme

fun HomeScreenViewController(
    onCompleteChallengeClick: () -> Unit,
    onProfileClick: () -> Unit,
): UIViewController = ComposeUIViewController {
    LeQuestAppTheme {
        val viewModel = viewModel { HomeScreenViewModel() }

        HomeScreenContent(
            viewModel = viewModel,
            onCompleteClick = onCompleteChallengeClick,
            onProfileClick = onProfileClick,
            onGetMeThereClick = {}
        )
    }
}