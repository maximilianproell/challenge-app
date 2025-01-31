package ui.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.LeQuestAppTheme
import ui.utils.koinViewModel

@Composable
fun ProfileScreenContent(modifier: Modifier) {

    val viewModel: ProfileScreenViewModel = koinViewModel()

    val screenState by viewModel.screenState.collectAsState()

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text(screenState.userName, style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(24.dp))

        Text(screenState.level, fontWeight = FontWeight.Bold)
        ProgressBar(
            progressPercent = screenState.xp / 1000f,
            barColor = MaterialTheme.colorScheme.primaryContainer,
            barText = {
                Text("${screenState.xp} / 1000 XP", color = MaterialTheme.colorScheme.onPrimaryContainer)
            },
            modifier = Modifier.fillMaxWidth().height(48.dp)
        )
    }
}

data class ProfileScreenState(
    val userName: String = "",
    val level: String = "",
    val xp: Int = 0,
)

@Composable
fun ProgressBar(
    progressPercent: Float,
    barColor: Color,
    barText: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .drawBehind {
                val height = size.height
                val width = size.width * progressPercent
                drawRect(color = barColor, size = Size(width = width, height = height))
            }
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(15.dp)),
        contentAlignment = Alignment.Center) {
        barText()
    }
}

@Composable
@Preview
fun ProgressBarPreview() {
    LeQuestAppTheme {
        ProgressBar(
            progressPercent = .8f,
            barColor = MaterialTheme.colorScheme.secondaryContainer,
            barText = {
                Text("1000 / 1200 XP", color = MaterialTheme.colorScheme.onSecondaryContainer)
            },
            modifier = Modifier.fillMaxWidth().height(48.dp)
        )

    }
}