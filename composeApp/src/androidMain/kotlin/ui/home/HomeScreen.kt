package ui.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
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
    val viewModel = viewModel { HomeScreenViewModel() }

    qrStringResultRecipient.onResult { qrCodeData ->
        Logger.withTag("HomeScreen").d { "Received qr code string on caller site: $qrCodeData" }
        viewModel.onQrCodeScanned(qrCodeData = qrCodeData)
    }

    HomeScreenContent(
        viewModel = viewModel,
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