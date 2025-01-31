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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.model.Quest
import lequestapp.lequestshared.generated.resources.Res
import lequestapp.lequestshared.generated.resources.general_complete
import lequestapp.lequestshared.generated.resources.home_accept_quest_button_text
import lequestapp.lequestshared.generated.resources.home_bottom_sheet_select_quest_label
import lequestapp.lequestshared.generated.resources.home_get_me_there_button_text
import lequestapp.lequestshared.generated.resources.settings
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ui.map.QuestsMap


data class HomeScreenState(
    val selectedQuest: Quest? = null,
    val loadingDataFromRemote: Boolean = false,
    val quests: List<Quest> = emptyList(),
    val errorStringResource: StringResource? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    viewModel: HomeScreenViewModel,
    onGetMeThereClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
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
                    .fillMaxWidth()
                    .padding(16.dp)
                    .windowInsetsPadding(WindowInsets.navigationBars),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (screenState.loadingDataFromRemote) {
                    // Waiting for remote data to load.
                    CircularProgressIndicator()
                } else {
                    screenState.selectedQuest?.let { selectedQuest ->
                        QuestModalBottomSheetContent(
                            quest = selectedQuest,
                            onAcceptQuestClick = viewModel::onQuestAccepted,
                            onGetMeThereClick = onGetMeThereClick,
                            onCompleteClick = onCompleteClick
                        )
                    }

                    if (screenState.selectedQuest == null) {
                        Text(
                            text = stringResource(Res.string.home_bottom_sheet_select_quest_label),
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
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

        screenState.errorStringResource?.let {
            AlertDialog(
                onDismissRequest = viewModel::onErrorMessageDismissed,
                confirmButton = {
                    TextButton(
                        onClick = viewModel::onErrorMessageDismissed
                    ) {
                        Text(text = "OK")
                    }
                },
                text = {
                    Text(text = stringResource(it))
                }
            )
        }
    }
}

@Composable
private fun ColumnScope.QuestModalBottomSheetContent(
    quest: Quest,
    onAcceptQuestClick: (Quest) -> Unit,
    onGetMeThereClick: () -> Unit,
    onCompleteClick: () -> Unit,
) {
    val timeLeftText = quest.timeToComplete?.let { timeLeft ->
        val hours = timeLeft / 60
        val minutes = timeLeft % 60
        "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')} hours left"
    }

    Text(
        text = quest.name,
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center,
    )

    if (timeLeftText != null) {
        Text(text = timeLeftText)
        Spacer(modifier = Modifier.height(16.dp))
    }

    Text(
        text = quest.description,
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(16.dp))

    if (quest.activationInfo == null) {
        Button(onClick = { onAcceptQuestClick(quest) }) {
            Icon(imageVector = Icons.Default.RocketLaunch, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(stringResource(Res.string.home_accept_quest_button_text))
        }
    } else {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = onGetMeThereClick) {
                Icon(imageVector = Icons.Default.Place, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(Res.string.home_get_me_there_button_text))
            }

            Spacer(Modifier.width(8.dp))

            Button(onClick = onCompleteClick) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(Res.string.general_complete))
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