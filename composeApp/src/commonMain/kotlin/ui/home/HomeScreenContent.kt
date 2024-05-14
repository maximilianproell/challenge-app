package ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import domain.model.Quest
import lequestapp.composeapp.generated.resources.Res
import lequestapp.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.stringResource
import ui.map.QuestsMap


data class HomeScreenState(
    val selectedQuest: Quest? = null,
    val quests: List<Quest> = emptyList(),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    onGetMeThereClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    val viewModel = viewModel { HomeScreenViewModel() }
    val screenState by viewModel.screenState.collectAsState()

    val scaffoldSheetState = rememberBottomSheetScaffoldState(
        SheetState(
            skipPartiallyExpanded = false,
            density = LocalDensity.current, initialValue = SheetValue.PartiallyExpanded,
            skipHiddenState = true
        )
    )
    BottomSheetScaffold(
        scaffoldState = scaffoldSheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .windowInsetsPadding(WindowInsets.navigationBars),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                screenState.selectedQuest?.let { selectedQuest ->
                    QuestModalBottomSheetContent(
                        quest = selectedQuest,
                        onAcceptQuestClick = viewModel::onQuestAccepted,
                        onGetMeThereClick = onCompleteClick,
                        onCompleteClick = onCompleteClick
                    )
                }
            }
        },
        sheetPeekHeight = 180.dp,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            QuestsMap(
                quests = screenState.quests,
                onQuestClick = viewModel::onQuestSelected,
            )

            ProfileButton(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(16.dp)
                    .align(Alignment.TopEnd),
                onClick = onProfileClick,
            )
        }
    }
}

// TODO: Just a placeholder
@Composable
private fun ColumnScope.QuestModalBottomSheetContent(
    quest: Quest,
    onAcceptQuestClick: (Quest) -> Unit,
    onGetMeThereClick: () -> Unit,
    onCompleteClick: () -> Unit,
) {
    Text(
        text = quest.name,
        style = MaterialTheme.typography.headlineMedium
    )
    // TODO: where does that come from?
    Text(text = "08:32 hours left")

    Spacer(modifier = Modifier.height(16.dp))

    Text(text = quest.description, style = MaterialTheme.typography.headlineSmall)
    // TODO: do we have more text here?
    Text(text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore...")

    Spacer(modifier = Modifier.height(16.dp))

    // TODO: add string resources

    if (!quest.isCurrentlyActive) {
        Button(onClick = { onAcceptQuestClick(quest) }) {
            Icon(imageVector = Icons.Default.RocketLaunch, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Challenge accepted!")
        }
    } else {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = onGetMeThereClick) {
                Icon(imageVector = Icons.Default.Place, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Get me there!")
            }

            Spacer(Modifier.width(8.dp))

            Button(onClick = onCompleteClick) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Complete")
            }
        }
    }
}

@Composable
private fun ProfileButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color.DarkGray)
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.Center),
            onClick = onClick
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = stringResource(Res.string.settings),
                tint = Color.White,
            )
        }
    }
}