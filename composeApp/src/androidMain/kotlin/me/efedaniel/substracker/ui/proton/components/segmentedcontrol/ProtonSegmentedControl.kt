package me.efedaniel.substracker.ui.proton.components.segmentedcontrol

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.efedaniel.substracker.ui.proton.components.text.ProtonText
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme
import me.efedaniel.substracker.ui.proton.tokens.dimension.ProtonDimension

/**
 * Design-system tonal segmented control. The selected segment lifts off the
 * [ProtonTheme.colors.surfaceContainerLow] track as a [ProtonTheme.colors.surfaceContainerLowest]
 * pill that slides between segments on selection. Generic over [items]; render each segment's
 * text via [labelText].
 */
@Composable
fun <T> ProtonSegmentedControl(
    items: List<T>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    labelText: (T) -> String,
    modifier: Modifier = Modifier,
) {
    val colors = ProtonTheme.colors
    val segmentShape = RoundedCornerShape(ProtonDimension.Corner12)
    BoxWithConstraints(
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    color = colors.surfaceContainerLow,
                    shape = RoundedCornerShape(ProtonDimension.Corner16),
                ).padding(all = ProtonDimension.Spacing4),
    ) {
        val segmentWidth = maxWidth / items.size
        val indicatorOffset by animateDpAsState(
            targetValue = segmentWidth * selectedIndex,
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
            label = "segmentIndicatorOffset",
        )
        // Single pill drawn behind the labels; slides to the selected segment instead of snapping.
        Box(modifier = Modifier.matchParentSize()) {
            Box(
                modifier =
                    Modifier
                        .offset(x = indicatorOffset)
                        .width(segmentWidth)
                        .fillMaxHeight()
                        .shadow(elevation = 1.dp, shape = segmentShape)
                        .background(color = colors.surfaceContainerLowest, shape = segmentShape),
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            items.forEachIndexed { index, item ->
                val selected = index == selectedIndex
                val textColor by animateColorAsState(
                    targetValue = if (selected) colors.onSurface else colors.onSurfaceVariant,
                    label = "segmentTextColor",
                )
                Box(
                    modifier =
                        Modifier
                            .weight(1f)
                            .heightIn(min = ProtonDimension.ComponentSize36)
                            .clip(segmentShape)
                            .selectable(
                                selected = selected,
                                role = Role.RadioButton,
                                onClick = { onSelected(index) },
                            ),
                    contentAlignment = Alignment.Center,
                ) {
                    ProtonText(
                        text = labelText(item),
                        style =
                            ProtonTheme.typography.bodyMedium.copy(
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                            ),
                        color = textColor,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun ProtonSegmentedControlPreview() {
    ProtonTheme {
        ProtonSegmentedControl(
            items = listOf("Monthly", "Annually", "One-time"),
            selectedIndex = 0,
            onSelected = {},
            labelText = { it },
            modifier = Modifier.padding(ProtonDimension.Spacing16),
        )
    }
}
