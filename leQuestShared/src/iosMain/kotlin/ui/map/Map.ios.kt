package ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import co.touchlab.kermit.Logger
import domain.model.Quest
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.MapKit.MKAnnotationView
import platform.MapKit.MKMapCamera.Companion.cameraLookingAtCenterCoordinate
import platform.MapKit.MKMapView
import platform.MapKit.MKMapViewDelegateProtocol
import platform.MapKit.MKPointAnnotation
import platform.darwin.NSObject

private val logger = Logger.withTag("Map.ios.kt")

@Composable
@OptIn(ExperimentalForeignApi::class)
actual fun QuestsMap(
    quests: List<Quest>,
    onQuestClick: (Quest) -> Unit,
) {

    val currentQuests = rememberUpdatedState(quests)
    val delegate = remember {
        MKDelegate { annotation ->
            // TODO: The annotation title is used to find the actual corresponding quest. This
            //  may be a problem, if multiple quests have the same title. A better solution would
            //  be to keep track of the annotation reference or something like that. However,
            //  no quest should have the same title as of right now.
            logger.d { "annotation title is ${annotation?.title}" }
            val selectedQuest = currentQuests.value.find { it.name == annotation?.title }
            logger.d { "annotation was clicked!! $selectedQuest" }
            selectedQuest?.let(onQuestClick)
        }
    }

    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            MKMapView().apply {
                setDelegate(delegate)
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

private class MKDelegate(
    private val onAnnotationClicked: (MKPointAnnotation?) -> Unit
) : NSObject(), MKMapViewDelegateProtocol {
    override fun mapView(mapView: MKMapView, didSelectAnnotationView: MKAnnotationView) {
        val annotation = didSelectAnnotationView.annotation as MKPointAnnotation
        onAnnotationClicked(annotation)
    }
}
