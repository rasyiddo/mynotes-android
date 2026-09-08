package com.example.mynotes_android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


// =====================================================
// PILIHAN TEMA
// =====================================================

enum class ThemeOption {
    PINK,
    SAGE,
    PURPLE,
    BLACK,
    WHITE
}


// =====================================================
// PINK
// =====================================================

private val PinkColorScheme = lightColorScheme(

    primary = Color(0xFFE91E63),
    onPrimary = Color.White,

    primaryContainer = Color(0xFFFFD9E5),
    onPrimaryContainer = Color(0xFF3F0018),

    secondary = Color(0xFF9C2752),
    onSecondary = Color.White,

    background = Color(0xFFFFF8F9),
    onBackground = Color(0xFF201A1B),

    surface = Color(0xFFFFF8F9),
    onSurface = Color(0xFF201A1B),

    surfaceVariant = Color(0xFFF3DDE2),
    onSurfaceVariant = Color(0xFF514347)
)


// =====================================================
// SAGE GREEN
// =====================================================

private val SageColorScheme = lightColorScheme(

    primary = Color(0xFF6B8E7B),
    onPrimary = Color.White,

    primaryContainer = Color(0xFFD5E8DB),
    onPrimaryContainer = Color(0xFF102017),

    secondary = Color(0xFF557363),
    onSecondary = Color.White,

    background = Color(0xFFF8FBF8),
    onBackground = Color(0xFF191D1A),

    surface = Color(0xFFF8FBF8),
    onSurface = Color(0xFF191D1A),

    surfaceVariant = Color(0xFFE0E8E2),
    onSurfaceVariant = Color(0xFF424943)
)


// =====================================================
// PURPLE
// =====================================================

private val PurpleColorScheme = lightColorScheme(

    primary = Color(0xFF7B4FA3),
    onPrimary = Color.White,

    primaryContainer = Color(0xFFEAD9F8),
    onPrimaryContainer = Color(0xFF2B123F),

    secondary = Color(0xFF6C5778),
    onSecondary = Color.White,

    background = Color(0xFFFCF8FF),
    onBackground = Color(0xFF1D1A20),

    surface = Color(0xFFFCF8FF),
    onSurface = Color(0xFF1D1A20),

    surfaceVariant = Color(0xFFE9DFEC),
    onSurfaceVariant = Color(0xFF49424C)
)


// =====================================================
// BLACK
// =====================================================

private val BlackColorScheme = darkColorScheme(

    primary = Color(0xFFFFFFFF),
    onPrimary = Color.Black,

    primaryContainer = Color(0xFF303030),
    onPrimaryContainer = Color.White,

    secondary = Color(0xFFCCCCCC),
    onSecondary = Color.Black,

    background = Color(0xFF000000),
    onBackground = Color.White,

    surface = Color(0xFF000000),
    onSurface = Color.White,

    surfaceVariant = Color(0xFF292929),
    onSurfaceVariant = Color(0xFFD0D0D0)
)


// =====================================================
// WHITE
// =====================================================

private val WhiteColorScheme = lightColorScheme(

    primary = Color(0xFF424242),
    onPrimary = Color.White,

    primaryContainer = Color(0xFFE5E5E5),
    onPrimaryContainer = Color(0xFF1A1A1A),

    secondary = Color(0xFF616161),
    onSecondary = Color.White,

    background = Color.White,
    onBackground = Color(0xFF1A1A1A),

    surface = Color.White,
    onSurface = Color(0xFF1A1A1A),

    surfaceVariant = Color(0xFFEDEDED),
    onSurfaceVariant = Color(0xFF4A4A4A)
)


// =====================================================
// MYNOTES THEME
// =====================================================

@Composable
fun MynotesandroidTheme(
    themeOption: ThemeOption = ThemeOption.WHITE,
    content: @Composable () -> Unit
) {

    val colorScheme = when (themeOption) {

        ThemeOption.PINK ->
            PinkColorScheme

        ThemeOption.SAGE ->
            SageColorScheme

        ThemeOption.PURPLE ->
            PurpleColorScheme

        ThemeOption.BLACK ->
            BlackColorScheme

        ThemeOption.WHITE ->
            WhiteColorScheme
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}