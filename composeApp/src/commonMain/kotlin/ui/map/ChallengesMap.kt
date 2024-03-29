package ui.map

import androidx.compose.runtime.Composable
import domain.model.Challenge

@Composable
expect fun ChallengesMap(challenges: List<Challenge>)