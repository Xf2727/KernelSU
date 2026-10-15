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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
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
    val palette = MaterialTheme.colorScheme
    val glassModifier = modifier.clip(shape).drawWithCache {
        val light = Brush.linearGradient(
            listOf(
                Color.White.copy(alpha = 0.16f),
                palette.primary.copy(alpha = 0.035f),
                palette.tertiary.copy(alpha = 0.09f),
            )
        )
        onDrawWithContent {
            drawContent()
            drawRect(light)
        }
    }
    val glassBorder = BorderStroke(
        1.dp,
        Brush.linearGradient(listOf(
            Color.White.copy(alpha = 0.42f),
            palette.primary.copy(alpha = 0.12f),
            palette.tertiary.copy(alpha = 0.28f),
        ))
    )
    val colors = CardDefaults.cardColors(
        containerColor = containerColor.copy(alpha = 0.88f),
        contentColor = contentColor,
    )
    when {
        onLongClick != null -> OutlinedCard(
            modifier = glassModifier
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
            border = glassBorder,
        ) { content() }

        onClick != null -> OutlinedCard(
            onClick = onClick,
            modifier = glassModifier,
            enabled = enabled,
            colors = colors,
            shape = shape,
            border = glassBorder,
            interactionSource = interactionSource,
        ) { content() }

        else -> OutlinedCard(
            modifier = glassModifier,
            colors = colors,
            shape = shape,
            border = glassBorder,
        ) { content() }
    }
}
