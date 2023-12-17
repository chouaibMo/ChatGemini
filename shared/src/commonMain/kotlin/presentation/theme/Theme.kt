package presentation.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LightColorScheme = lightColorScheme(
    tertiaryContainer = Gray400,
    onPrimaryContainer = Color.Black.copy(alpha = 0.8f),
    onSecondaryContainer = Color.LightGray,
    surface = Gray400
)

private val DarkColorScheme = darkColorScheme(
    primaryContainer = SurfaceDark,
    tertiaryContainer = SurfaceDark,
    onPrimaryContainer = Color.White,
    onSecondaryContainer = Gray700,
    surface = BackgroundDark
)

private val shapes = Shapes(
    extraSmall = RoundedCornerShape(3.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

private val typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )
)

@Composable
internal fun ChatGeminiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme,
        typography = typography,
        shapes = shapes,
        content = { Surface(content = content) }
    )
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)