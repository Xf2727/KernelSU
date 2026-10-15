package me.weishu.kernelsu.ui.component.material

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import top.yukonga.miuix.kmp.blur.LayerBackdrop
import top.yukonga.miuix.kmp.blur.layerBackdrop
import top.yukonga.miuix.kmp.blur.rememberLayerBackdrop

internal val LocalGlassBackdrop = staticCompositionLocalOf<LayerBackdrop?> { null }

@Composable
fun GlassBackdrop(content: @Composable () -> Unit) {
    val backdrop = rememberLayerBackdrop()
    Box {
        Canvas(Modifier.matchParentSize().layerBackdrop(backdrop)) {
            drawRect(Color(0xFF132D46))
            drawRect(Brush.radialGradient(
                listOf(Color(0xFF51DDB5), Color.Transparent),
                center = Offset(size.width * 0.9f, size.height * 0.22f),
                radius = size.width * 0.95f,
            ))
            drawRect(Brush.radialGradient(
                listOf(Color(0xFF427CC5), Color.Transparent),
                center = Offset(size.width * 0.05f, size.height * 0.8f),
                radius = size.width * 1.1f,
            ))
        }
        CompositionLocalProvider(LocalGlassBackdrop provides backdrop, content = content)
    }
}
