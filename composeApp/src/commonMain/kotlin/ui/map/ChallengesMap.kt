package ui.map

import androidx.compose.runtime.Composable
import domain.model.Quest

@Composable
expect fun ChallengesMap(quests: List<Quest>)