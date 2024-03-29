import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.ChallengeAppTheme
import ui.home.HomeScreen

@Composable
@Preview
fun App() {
    ChallengeAppTheme {
        Navigator(
            screen = HomeScreen
        )
    }
}