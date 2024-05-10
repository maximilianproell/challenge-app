package ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import co.touchlab.kermit.Logger
import domain.model.Quest
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.MapKit.MKMapCamera.Companion.cameraLookingAtCenterCoordinate
import platform.MapKit.MKMapView
import platform.MapKit.MKPointAnnotation

private val logger = Logger.withTag("Map.ios.kt")

@Composable
@OptIn(ExperimentalForeignApi::class)
actual fun ChallengesMap(
    quests: List<Quest>,
    onQuestClick: (Quest) -> Unit,
) {
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

                // setDelegate(MyMKMapViewDelegate())
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

// Looks like this is on hold until https://youtrack.jetbrains.com/issue/KT-67641/KMP-Return-type-of-is-not-a-subtype-of-the-return-type-of-the-overridden-member
//  is fixed.
/*
class MyMKMapViewDelegate : NSObject(), MKMapViewDelegateProtocol {

    override fun mapView(mapView: MKMapView, viewForOverlay: MKOverlayProtocol): MKOverlayView? {
        logger.d { "map view click on annotation?" }
        return null
    }
}*/
