package presentation.ui.screen

import domain.Message
import domain.Status

data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val status: Status = Status.IDLE,
    val apiKey: String = ""
)