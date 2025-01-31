package ui.scanner

import android.Manifest
import android.net.Uri
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import co.touchlab.kermit.Logger
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.result.ResultBackNavigator
import lequestapp.lequestshared.generated.resources.Res
import lequestapp.lequestshared.generated.resources.allow_camera_permission
import org.jetbrains.compose.resources.stringResource

private val logger = Logger.withTag("QrCodeScannerScreen")

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Destination<RootGraph>
fun QrCodeScannerScreen(
    resultNavigator: ResultBackNavigator<String>
) {
    val currentContext = LocalContext.current
    val currentLifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember(currentContext) { LifecycleCameraController(currentContext) }

    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )

    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
    }

    val barcodeScanner = remember {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        BarcodeScanning.getClient(options)
    }

    DisposableEffect(currentLifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                barcodeScanner.close()
            }
        }

        currentLifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            currentLifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (cameraPermissionState.status.isGranted) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                val previewView = PreviewView(context)
                cameraController.setImageAnalysisAnalyzer(
                    ContextCompat.getMainExecutor(currentContext),
                    MlKitAnalyzer(
                        listOf(barcodeScanner),
                        CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED,
                        ContextCompat.getMainExecutor(currentContext)
                    ) { result: MlKitAnalyzer.Result? ->
                        val barcodeResults = result?.getValue(barcodeScanner)
                        if ((barcodeResults == null) ||
                            (barcodeResults.size == 0) ||
                            (barcodeResults.first() == null)
                        ) {
                            previewView.overlay.clear()
                            return@MlKitAnalyzer
                        }

                        val barcode = barcodeResults[0]
                        val uri = Uri.parse(barcode.rawValue.toString())
                        logger.d("Scanned QR Code: $uri")
                        if (uri.host == "qr.lequest.gg") {
                            barcodeScanner.close()
                            resultNavigator.navigateBack(
                                uri.getQueryParameter("c").toString()
                            )
                        }
                    }
                )

                cameraController.bindToLifecycle(currentLifecycleOwner)
                previewView.controller = cameraController
                previewView
            }
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(Res.string.allow_camera_permission),
                textAlign = TextAlign.Center,
            )
        }
    }
}