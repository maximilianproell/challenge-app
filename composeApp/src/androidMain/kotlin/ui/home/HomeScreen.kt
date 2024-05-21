package ui.home

import androidx.compose.runtime.Composable
import co.touchlab.kermit.Logger
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.ProfileScreenDestination
import com.ramcosta.composedestinations.generated.destinations.QrCodeScannerScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.result.onResult

@Composable
@Destination<RootGraph>(
    start = true
)
fun HomeScreen(
    navigator: DestinationsNavigator,
    qrStringResultRecipient: ResultRecipient<QrCodeScannerScreenDestination, String>
) {
    qrStringResultRecipient.onResult { uriString ->
        Logger.withTag("HomeScreen").d { "Received qr code string on caller site: $uriString" }
    }

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