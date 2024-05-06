package ui.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ui.components.PlatformBackArrow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination<RootGraph>
fun ProfileScreen(
    navigator: DestinationsNavigator
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Profile")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigator.navigateUp()
                        }
                    ) {
                        PlatformBackArrow()
                    }
                }
            )
        }
    ) { scaffoldPaddingValues ->
        ProfileScreenContent(
            modifier = Modifier
                .padding(scaffoldPaddingValues)
                .fillMaxSize()
        )
    }
}