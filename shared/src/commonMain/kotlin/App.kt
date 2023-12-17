import androidx.compose.runtime.Composable
import presentation.theme.ChatGeminiTheme
import presentation.ui.screen.ChatScreen

@Composable
fun App() {
    ChatGeminiTheme {
        ChatScreen()
    }
}

expect fun getPlatformName(): String