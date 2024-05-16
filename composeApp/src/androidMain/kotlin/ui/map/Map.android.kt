package ui.map

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import domain.model.Quest

@Composable
actual fun QuestsMap(
    quests: List<Quest>,
    onQuestClick: (Quest) -> Unit,
) {
    val berlin = LatLng(52.52000660, 13.40495400)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(berlin, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        uiSettings = MapUiSettings(
            compassEnabled = false,
            mapToolbarEnabled = false,
            zoomControlsEnabled = false,
        ),
    ) {
        quests.forEach { quest: Quest ->
            AdvancedMarker(
                state = MarkerState(
                    position = LatLng(
                        quest.activationGeoLocation.latitude,
                        quest.activationGeoLocation.longitude
                    ),
                ),
                title = quest.name,
                alpha = if (quest.isClickable) 1.0f else .3f,
                onClick = {
                    if (quest.isClickable) {
                        onQuestClick(quest)
                        false
                    } else true
                }
            )
        }
    }
}