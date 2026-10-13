package me.weishu.kernelsu.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val CodexsuNavy = Color(0xFF112C42)
val CodexsuMint = Color(0xFF5CE1C5)
val CodexsuBrandText = Color(0xFFE1F4F1)
val CodexsuBrandMuted = Color(0xFFB1CDD8)

fun codexsuColorScheme(isDark: Boolean, isAmoled: Boolean): ColorScheme {
    val scheme = if (isDark) darkColorScheme(
        primary = CodexsuMint,
        onPrimary = CodexsuNavy,
        primaryContainer = Color(0xFF17463F),
        onPrimaryContainer = Color(0xFFB1F5E5),
        secondary = Color(0xFFABCBDD),
        onSecondary = CodexsuNavy,
        secondaryContainer = Color(0xFF203E55),
        onSecondaryContainer = CodexsuBrandText,
        tertiary = CodexsuMint,
        onTertiary = CodexsuNavy,
        tertiaryContainer = Color(0xFF17463F),
        onTertiaryContainer = Color(0xFFB1F5E5),
        background = Color(0xFF0B1925),
        onBackground = CodexsuBrandText,
        surface = Color(0xFF142C3E),
        onSurface = CodexsuBrandText,
        surfaceVariant = Color(0xFF274253),
        onSurfaceVariant = CodexsuBrandMuted,
        surfaceDim = Color(0xFF0B1925),
        surfaceBright = Color(0xFF203B4E),
        surfaceContainerLowest = Color(0xFF08141E),
        surfaceContainerLow = Color(0xFF0B1925),
        surfaceContainer = Color(0xFF142C3E),
        surfaceContainerHigh = Color(0xFF1A3346),
        surfaceContainerHighest = Color(0xFF203B4E),
        outline = Color(0xFF7895A3),
        outlineVariant = Color(0xFF355262),
        inverseSurface = Color(0xFFE6F0ED),
        inverseOnSurface = CodexsuNavy,
        inversePrimary = Color(0xFF006B5B),
        surfaceTint = CodexsuMint,
    ) else lightColorScheme(
        primary = CodexsuNavy,
        onPrimary = CodexsuBrandText,
        primaryContainer = Color(0xFFB1F0E1),
        onPrimaryContainer = CodexsuNavy,
        secondary = Color(0xFF006B5B),
        onSecondary = Color.White,
        secondaryContainer = Color(0xFFD4ECE7),
        onSecondaryContainer = CodexsuNavy,
        tertiary = Color(0xFF006B5B),
        onTertiary = Color.White,
        tertiaryContainer = Color(0xFFD0F4EA),
        onTertiaryContainer = Color(0xFF004E42),
        background = Color(0xFFE6F0ED),
        onBackground = CodexsuNavy,
        surface = Color(0xFFF5FAF8),
        onSurface = CodexsuNavy,
        surfaceVariant = Color(0xFFD4E6E2),
        onSurfaceVariant = Color(0xFF4F6874),
        surfaceDim = Color(0xFFD6E6E1),
        surfaceBright = Color(0xFFF5FAF8),
        surfaceContainerLowest = Color.White,
        surfaceContainerLow = Color(0xFFE6F0ED),
        surfaceContainer = Color(0xFFDFEDE8),
        surfaceContainerHigh = Color(0xFFD7E9E3),
        surfaceContainerHighest = Color(0xFFCFE4DC),
        outline = Color(0xFF6C8A91),
        outlineVariant = Color(0xFFBED7D2),
        inverseSurface = CodexsuNavy,
        inverseOnSurface = CodexsuBrandText,
        inversePrimary = CodexsuMint,
        surfaceTint = Color(0xFF006B5B),
    )
    return scheme.amoledBackground(isAmoled)
}
