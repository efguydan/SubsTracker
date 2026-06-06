package me.efedaniel.substracker.ui.proton.components.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import me.efedaniel.substracker.ui.proton.theme.ProtonTheme
import me.efedaniel.substracker.ui.proton.tokens.dimension.ProtonDimension

/**
 * Design-system modal bottom sheet: rounded top corners, tonal surface, and a slim
 * centered drag handle. Content layout is entirely up to the caller.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProtonBottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier,
    containerColor: Color = ProtonTheme.colors.surface,
    shape: Shape =
        RoundedCornerShape(
            topStart = ProtonDimension.Corner24,
            topEnd = ProtonDimension.Corner24,
        ),
    dragHandle: @Composable () -> Unit = { ProtonBottomSheetDragHandle() },
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier,
        containerColor = containerColor,
        shape = shape,
        dragHandle = dragHandle,
        content = content,
    )
}

@Composable
private fun ProtonBottomSheetDragHandle(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .padding(top = ProtonDimension.Spacing12, bottom = ProtonDimension.Spacing4)
                .size(
                    width = ProtonDimension.DragHandleWidth40,
                    height = ProtonDimension.DragHandleHeight4,
                ).background(
                    color = ProtonTheme.colors.surfaceContainerHighest,
                    shape = RoundedCornerShape(percent = 50),
                ),
    )
}
