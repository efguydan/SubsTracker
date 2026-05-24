package me.efedaniel.substracker.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.efedaniel.substracker.ui.proton.components.text.ProtonText
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme
import me.efedaniel.substracker.ui.proton.tokens.dimension.ProtonDimension

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
    val typography = ProtonTheme.typography
    LazyColumn(
        modifier = modifier
            .background(colors.surface)
            .fillMaxSize(),
        contentPadding = PaddingValues(
            top = ProtonDimension.Spacing16,
            bottom = ProtonDimension.Spacing24,
            start = ProtonDimension.Spacing20,
            end = ProtonDimension.Spacing20
        ),
        verticalArrangement = Arrangement.spacedBy(ProtonDimension.Spacing16)
    ) {
        item {
            HeroSpendCard(
                label = state.averageMonthlySpendLabel,
                value = state.averageMonthlySpendValue,
                delta = state.deltaPercent
            )
        }
        item {
            InsightCard(
                title = state.insightTitle,
                caption = state.insightCaption
            )
        }
        item {
            ProtonText(
                text = state.timelineTitle,
                style = typography.titleMedium,
                color = colors.onSurface
            )
        }
        state.sections.forEach { section ->
            item {
                ProtonText(
                    text = section.label,
                    style = typography.labelMedium,
                    color = colors.onSurface.copy(alpha = 0.6f)
                )
            }
            items(section.items, key = { it.name }) { item ->
                TimelineRow(item = item)
            }
        }
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
    val typography = ProtonTheme.typography
    Column(modifier = modifier) {
        ProtonText(
            text = label,
            style = typography.labelMedium,
            color = colors.onSurface.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(ProtonDimension.Spacing8))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            ProtonText(
                text = value,
                style = typography.displayLarge,
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
            style = ProtonTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = colors.secondary
        )
    }
}

@Composable
private fun InsightCard(
    title: String,
    caption: String,
    modifier: Modifier = Modifier
) {
    val colors = ProtonTheme.colors
    val typography = ProtonTheme.typography
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colors.surfaceContainerLowest),
        shape = RoundedCornerShape(ProtonDimension.Corner16),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(ProtonDimension.Spacing16)) {
            ProtonText(
                text = title,
                style = typography.titleMedium,
                color = colors.onSurface
            )
            Spacer(modifier = Modifier.height(ProtonDimension.Spacing16))
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
            Spacer(modifier = Modifier.height(ProtonDimension.Spacing12))
            ProtonText(
                text = caption,
                style = typography.labelMedium,
                color = colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun InsightBar(
    height: Dp,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
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
    val typography = ProtonTheme.typography
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = colors.surfaceContainerLowest,
        shape = RoundedCornerShape(ProtonDimension.Corner16)
    ) {
        Row(
            modifier = Modifier.padding(ProtonDimension.Spacing12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(ProtonDimension.Corner12))
                    .background(colors.surfaceContainerLow)
            )
            Spacer(modifier = Modifier.width(ProtonDimension.Spacing12))
            Column(modifier = Modifier.weight(1f)) {
                ProtonText(
                    text = item.name,
                    style = typography.titleMedium,
                    color = colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(ProtonDimension.Spacing4))
                ProtonText(
                    text = item.subtitle,
                    style = typography.bodyMedium,
                    color = colors.onSurface.copy(alpha = 0.6f)
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                ProtonText(
                    text = item.amount,
                    style = typography.titleMedium,
                    color = colors.onSurface
                )
                Spacer(modifier = Modifier.height(ProtonDimension.Spacing4))
                ProtonText(
                    text = item.dueLabel,
                    style = typography.labelMedium,
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
        HomeScreen(state = sampleHomeState)
    }
}
