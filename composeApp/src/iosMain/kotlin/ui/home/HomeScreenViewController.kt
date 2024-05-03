package ui.home
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun HomeScreenViewController(
    onCompleteChannengeClick: () -> Unit,
    onProfileClick: () -> Unit,
): UIViewController = ComposeUIViewController {
    HomeScreen(
        onCompleteChannengeClick = onCompleteChannengeClick,
        onProfileClick = onProfileClick,
    )
}