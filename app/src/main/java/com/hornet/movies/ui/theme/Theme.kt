package com.hornet.movies.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val PastelLilac = Color(0xFFEBD6FD)
private val PastelBlush = Color(0xFFFFD6DC)
private val PastelMint = Color(0xFFD0F0C0)
private val PastelSky = Color(0xFFD6F0FF)
private val PastelSurfaceLight = Color(0xFFFFFEFC)
private val PastelBackgroundLight = Color(0xFFFAFAFA)

private val PastelLilacDark = Color(0xFFBFA8D9)
private val PastelBlushDark = Color(0xFFDC9BA6)
private val PastelMintDark = Color(0xFF9ACFA2)
private val PastelSkyDark = Color(0xFF9FC7D9)
private val PastelSurfaceDark = Color(0xFF1E1E2A)
private val PastelBackgroundDark = Color(0xFF121212)

private val LightColorScheme = lightColorScheme(
    primary = PastelLilac,
    onPrimary = Color.Black,
    secondary = PastelBlush,
    onSecondary = Color.Black,
    tertiary = PastelMint,
    onTertiary = Color.Black,
    background = PastelBackgroundLight,
    onBackground = Color.Black,
    surface = PastelSurfaceLight,
    onSurface = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary = PastelLilacDark,
    onPrimary = Color.White,
    secondary = PastelBlushDark,
    onSecondary = Color.White,
    tertiary = PastelMintDark,
    onTertiary = Color.White,
    background = PastelBackgroundDark,
    onBackground = Color.White,
    surface = PastelSurfaceDark,
    onSurface = Color.White
)

@Composable
fun HornetMoviesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}