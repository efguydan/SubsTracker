package me.efedaniel.substracker.ui.proton.components.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import me.efedaniel.substracker.ui.proton.components.text.ProtonText
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme
import me.efedaniel.substracker.ui.proton.tokens.dimension.ProtonDimension
import me.efedaniel.substracker.utility.extensions.conditional

/**
 * Design-system tonal text field: label above, borderless [ProtonTheme.colors.surfaceContainerLow]
 * container, optional helper text below, and an optional trailing icon.
 *
 * Pass [onClick] to render the field as a non-editable trigger (e.g. for dropdowns or
 * date pickers): the container becomes clickable and shows [value] or [placeholder] as text.
 */
@Composable
fun ProtonTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    helperText: String? = null,
    helperTextStyle: TextStyle = ProtonTheme.typography.labelMedium,
    trailingIcon: (@Composable () -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
) {
    val colors = ProtonTheme.colors
    val containerShape = RoundedCornerShape(ProtonDimension.Corner16)

    Column(modifier = modifier) {
        label?.let {
            ProtonText(
                text = it,
                style = ProtonTheme.typography.labelMedium,
                color = colors.onSurfaceVariant,
                modifier =
                    Modifier.padding(
                        start = ProtonDimension.Spacing4,
                        bottom = ProtonDimension.Spacing8,
                    ),
            )
        }
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = ProtonDimension.ComponentSize48)
                    .clip(containerShape)
                    .background(color = colors.surfaceContainerLow, shape = containerShape)
                    .conditional(onClick != null && enabled) { clickable { onClick?.invoke() } }
                    .padding(all = ProtonDimension.Spacing16),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.weight(1f)) {
                if (onClick != null) {
                    TriggerFieldText(value = value, placeholder = placeholder)
                } else {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        textStyle = ProtonTheme.typography.bodyLarge.copy(color = colors.onSurface),
                        keyboardOptions = keyboardOptions,
                        singleLine = singleLine,
                        enabled = enabled,
                        cursorBrush = SolidColor(colors.primary),
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            if (value.isEmpty() && placeholder != null) {
                                PlaceholderText(text = placeholder)
                            }
                            innerTextField()
                        },
                    )
                }
            }
            trailingIcon?.let {
                Spacer(modifier = Modifier.width(ProtonDimension.Spacing8))
                it()
            }
        }
        helperText?.let {
            ProtonText(
                text = it,
                style = helperTextStyle,
                color = colors.onSurfaceVariant.copy(alpha = 0.7f),
                modifier =
                    Modifier.padding(
                        start = ProtonDimension.Spacing4,
                        top = ProtonDimension.Spacing8,
                    ),
            )
        }
    }
}

@Composable
private fun TriggerFieldText(
    value: String,
    placeholder: String?,
) {
    if (value.isEmpty() && placeholder != null) {
        PlaceholderText(text = placeholder)
    } else {
        ProtonText(
            text = value,
            style = ProtonTheme.typography.bodyLarge,
            color = ProtonTheme.colors.onSurface,
        )
    }
}

@Composable
private fun PlaceholderText(text: String) {
    ProtonText(
        text = text,
        style = ProtonTheme.typography.bodyLarge,
        color = ProtonTheme.colors.outline.copy(alpha = 0.5f),
    )
}

@Preview(showBackground = true)
@Composable
internal fun ProtonTextFieldPreview() {
    ProtonTheme {
        Column(modifier = Modifier.padding(ProtonDimension.Spacing16)) {
            ProtonTextField(
                value = "",
                onValueChange = {},
                label = "Name",
                placeholder = "e.g. Netflix",
            )
            Spacer(modifier = Modifier.heightIn(min = ProtonDimension.Spacing16))
            ProtonTextField(
                value = "24",
                onValueChange = {},
                label = "Billing day",
                helperText = "On short months we'll bill on the last day",
            )
        }
    }
}
