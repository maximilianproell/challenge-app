package ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import domain.model.Quest
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.MapKit.MKMapCamera.Companion.cameraLookingAtCenterCoordinate
import platform.MapKit.MKMapView
import platform.MapKit.MKPointAnnotation

@Composable
@OptIn(ExperimentalForeignApi::class)
actual fun ChallengesMap(quests: List<Quest>) {
    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            MKMapView().apply {
                this.setCamera(
                    cameraLookingAtCenterCoordinate(
                        CLLocationCoordinate2DMake(
                            52.52000660, 13.4049
                        ),
                        CLLocationCoordinate2DMake(
                            52.52000660, 13.4049
                        ),
                        100_000.0
                    )
                )
            }
        },
        update = { mapView ->
            quests.forEach { quest: Quest ->
                val coordinates = CLLocationCoordinate2DMake(
                    latitude = quest.activationGeoLocation.latitude,
                    longitude = quest.activationGeoLocation.longitude
                )
                val mapAnnotation = MKPointAnnotation()
                mapAnnotation.setCoordinate(coordinates)
                mapAnnotation.setTitle(quest.name)
                mapView.addAnnotation(mapAnnotation)
            }
        }
    )
}