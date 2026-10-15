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
import androidx.compose.runtime.getValue
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.graphics.graphicsLayer
import top.yukonga.miuix.kmp.blur.drawBackdrop
import top.yukonga.miuix.kmp.blur.blur
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.luminance
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
    val backdrop = LocalGlassBackdrop.current
    val dark = palette.surface.luminance() < 0.5f
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (pressed && enabled) 0.98f else 1f, label = "glassPress")
    val glassModifier = modifier.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }.clip(shape).then(
        if (backdrop != null) Modifier.drawBackdrop(
            backdrop = backdrop,
            shape = { shape },
            effects = { blur(24.dp.toPx(), 24.dp.toPx()) },
        ) else Modifier
    ).drawWithCache {
        val light = Brush.linearGradient(
            listOf(
                Color.White.copy(alpha = if (dark) 0.12f else 0.45f),
                Color.Transparent,
                palette.tertiary.copy(alpha = 0.07f),
            )
        )
        onDrawWithContent {
            drawRect(light)
            drawContent()
        }
    }
    val glassBorder = BorderStroke(
        1.dp,
        Brush.linearGradient(listOf(
            Color.White.copy(alpha = if (dark) 0.30f else 0.75f),
            palette.outlineVariant.copy(alpha = 0.16f),
            palette.tertiary.copy(alpha = 0.22f),
        ))
    )
    val colors = CardDefaults.cardColors(
        containerColor = containerColor.copy(alpha = if (backdrop != null) {
            if (dark) 0.82f else 0.90f
        } else 0.96f),
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
