package com.example.mynotes_android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
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

    primaryContainer = Color(0xFFFFB1C8),
    onPrimaryContainer = Color(0xFF3E0018),

    secondary = Color(0xFFAD1457),
    onSecondary = Color.White,

    secondaryContainer = Color(0xFFFFD9E5),
    onSecondaryContainer = Color(0xFF3E0018),

    background = Color(0xFFFFE4EC),
    onBackground = Color(0xFF2B1018),

    surface = Color(0xFFFFF0F4),
    onSurface = Color(0xFF2B1018),

    surfaceVariant = Color(0xFFF8C9D6),
    onSurfaceVariant = Color(0xFF5C3943)
)


// =====================================================
// SAGE GREEN
// =====================================================

private val SageColorScheme = lightColorScheme(

    primary = Color(0xFF557A64),
    onPrimary = Color.White,

    primaryContainer = Color(0xFFB8D5C1),
    onPrimaryContainer = Color(0xFF102017),

    secondary = Color(0xFF6F8F7B),
    onSecondary = Color.White,

    secondaryContainer = Color(0xFFD5E8DB),
    onSecondaryContainer = Color(0xFF17271D),

    background = Color(0xFFDCEBDD),
    onBackground = Color(0xFF172019),

    surface = Color(0xFFEEF6EF),
    onSurface = Color(0xFF172019),

    surfaceVariant = Color(0xFFC8DDCC),
    onSurfaceVariant = Color(0xFF3E5143)
)


// =====================================================
// PURPLE
// =====================================================

private val PurpleColorScheme = lightColorScheme(

    primary = Color(0xFF7B3FA0),
    onPrimary = Color.White,

    primaryContainer = Color(0xFFDDB8F0),
    onPrimaryContainer = Color(0xFF2B1239),

    secondary = Color(0xFF765487),
    onSecondary = Color.White,

    secondaryContainer = Color(0xFFEAD7F2),
    onSecondaryContainer = Color(0xFF281330),

    background = Color(0xFFEADCF2),
    onBackground = Color(0xFF211426),

    surface = Color(0xFFF5ECF8),
    onSurface = Color(0xFF211426),

    surfaceVariant = Color(0xFFDCC9E5),
    onSurfaceVariant = Color(0xFF4E3B55)
)


// =====================================================
// BLACK
// =====================================================

private val BlackColorScheme = darkColorScheme(

    primary = Color.White,
    onPrimary = Color.Black,

    primaryContainer = Color(0xFF333333),
    onPrimaryContainer = Color.White,

    secondary = Color(0xFFCCCCCC),
    onSecondary = Color.Black,

    secondaryContainer = Color(0xFF444444),
    onSecondaryContainer = Color.White,

    background = Color.Black,
    onBackground = Color.White,

    surface = Color(0xFF101010),
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

    secondaryContainer = Color(0xFFEDEDED),
    onSecondaryContainer = Color(0xFF1A1A1A),

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
        typography = Typography(),
        content = content
    )
}