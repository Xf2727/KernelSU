package me.weishu.kernelsu.ui.component.material

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TonalCard(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(containerColor),
    shape: Shape = MaterialTheme.shapes.large,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val colors = CardDefaults.cardColors(
        containerColor = containerColor,
        contentColor = contentColor,
    )
    when {
        onLongClick != null -> OutlinedCard(
            modifier = modifier
                .clip(shape)
                .combinedClickable(
                    enabled = enabled,
                    onClick = onClick ?: {},
                    onLongClick = onLongClick,
                    interactionSource = interactionSource,
                    indication = LocalIndication.current,
                ),
            colors = colors,
            shape = shape,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
        ) { content() }

        onClick != null -> OutlinedCard(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            colors = colors,
            shape = shape,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
            interactionSource = interactionSource,
        ) { content() }

        else -> OutlinedCard(
            modifier = modifier,
            colors = colors,
            shape = shape,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
        ) { content() }
    }
}
