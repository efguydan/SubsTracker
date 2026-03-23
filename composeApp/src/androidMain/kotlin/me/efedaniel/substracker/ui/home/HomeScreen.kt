package me.efedaniel.substracker.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.efedaniel.substracker.ui.proton.components.text.ProtonText
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme

@Composable
fun HomeRoute(modifier: Modifier = Modifier) {
    HomeScreen(
        state = sampleHomeState,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    modifier: Modifier = Modifier
) {
    val colors = ProtonTheme.colors
    HomeTimelineContent(
        state = state,
        modifier = modifier
            .background(colors.surface)
            .fillMaxSize()
            .padding(top = 8.dp)
    )
}

@Composable
private fun HomeTimelineContent(
    state: HomeUiState,
    modifier: Modifier = Modifier
) {
    val colors = ProtonTheme.colors
    LazyColumn(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
            ProtonText(
                text = state.title,
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                color = colors.onSurface
            )
        }
        item {
            HeroSpendCard(
                label = state.averageMonthlySpendLabel,
                value = state.averageMonthlySpendValue,
                delta = state.deltaPercent
            )
        }
        item {
            InsightCard(title = state.insightTitle)
        }
        item {
            ProtonText(
                text = state.timelineTitle,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                color = colors.onSurface
            )
        }
        state.sections.forEach { section ->
            item {
                ProtonText(
                    text = section.label,
                    style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
                    color = colors.onSurface.copy(alpha = 0.6f)
                )
            }
            items(section.items, key = { it.name }) { item ->
                TimelineRow(item = item)
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
private fun HeroSpendCard(
    label: String,
    value: String,
    delta: String,
    modifier: Modifier = Modifier
) {
    val colors = ProtonTheme.colors
    Column(modifier = modifier) {
        ProtonText(
            text = label,
            style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
            color = colors.onSurface.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            ProtonText(
                text = value,
                style = androidx.compose.material3.MaterialTheme.typography.displayLarge,
                color = colors.primary
            )
            DeltaChip(text = delta)
        }
    }
}

@Composable
private fun DeltaChip(
    text: String,
    modifier: Modifier = Modifier
) {
    val colors = ProtonTheme.colors
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(colors.secondary.copy(alpha = 0.15f))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        ProtonText(
            text = text,
            style = androidx.compose.material3.MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = colors.secondary
        )
    }
}

@Composable
private fun InsightCard(
    title: String,
    modifier: Modifier = Modifier
) {
    val colors = ProtonTheme.colors
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colors.surfaceContainerLowest),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ProtonText(
                text = title,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                color = colors.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                InsightBar(height = 48.dp, color = colors.primary)
                InsightBar(height = 64.dp, color = colors.primary.copy(alpha = 0.9f))
                InsightBar(height = 52.dp, color = colors.primary.copy(alpha = 0.8f))
                InsightBar(height = 72.dp, color = colors.secondary)
            }
            Spacer(modifier = Modifier.height(12.dp))
            ProtonText(
                text = "ACTIVE VS TRIAL PERIODS",
                style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
                color = colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun InsightBar(
    height: androidx.compose.ui.unit.Dp,
    color: androidx.compose.ui.graphics.Color
) {
    Box(
        modifier = Modifier
            .width(48.dp)
            .height(height)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
    )
}

@Composable
private fun TimelineRow(
    item: TimelineItem,
    modifier: Modifier = Modifier
) {
    val colors = ProtonTheme.colors
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = colors.surfaceContainerLowest,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(colors.surfaceContainerLow)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                ProtonText(
                    text = item.name,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                    color = colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                ProtonText(
                    text = item.subtitle,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                    color = colors.onSurface.copy(alpha = 0.6f)
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                ProtonText(
                    text = item.amount,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                    color = colors.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                ProtonText(
                    text = item.dueLabel,
                    style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
                    color = colors.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomePreview() {
    ProtonTheme(darkTheme = false) {
        HomeScreen(
            state = sampleHomeState,
        )
    }
}
