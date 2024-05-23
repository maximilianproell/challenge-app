package ui.map

import androidx.compose.runtime.Composable
import domain.model.Quest

@Composable
expect fun QuestsMap(
    quests: List<Quest>,
    onQuestClick: (Quest) -> Unit,
)