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
import domain.model.Challenge

@Composable
actual fun ChallengesMap(challenges: List<Challenge>) {
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
        challenges.forEach { challenge: Challenge ->
            AdvancedMarker(
                state = MarkerState(
                    position = LatLng(
                        challenge.activationGeoLocation.latitude,
                        challenge.activationGeoLocation.longitude
                    ),
                ),
                title = challenge.name
            )
        }
    }
}