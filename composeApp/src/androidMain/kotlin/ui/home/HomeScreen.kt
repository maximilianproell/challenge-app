package ui.home

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.ProfileScreenDestination
import com.ramcosta.composedestinations.generated.destinations.QrCodeScannerScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>(
    start = true
)
fun HomeScreen(
    navigator: DestinationsNavigator
) {

    HomeScreenContent(
        onGetMeThereClick = {
            // TODO
        },
        onCompleteClick = {
            navigator.navigate(QrCodeScannerScreenDestination)
        },
        onProfileClick = {
            navigator.navigate(ProfileScreenDestination)
        }
    )
}