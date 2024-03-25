package ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.MapKit.MKMapView
import platform.MapKit.MKPointAnnotation

@Composable
@OptIn(ExperimentalForeignApi::class)
actual fun ChallengesMap() {
    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            MKMapView()
        },
        update = { mapView ->
            val mapAnnotation = MKPointAnnotation()

            val coordinates = CLLocationCoordinate2DMake(52.52000660, 13.40495400)

            mapAnnotation.setCoordinate(coordinates)
            mapAnnotation.setTitle("This is Berlin")
            mapView.addAnnotation(mapAnnotation)
            mapView.setSelectedAnnotations(listOf(mapAnnotation))
        }
    )
}