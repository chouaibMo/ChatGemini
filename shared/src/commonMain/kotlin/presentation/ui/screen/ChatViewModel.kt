package presentation.ui.screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import data.GeminiRepositoryImpl
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.GeminiRepository
import domain.Message
import domain.Sender
import domain.Status
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val geminiRepository: GeminiRepository = GeminiRepositoryImpl()

    private val _uiState = mutableStateOf(ChatUiState())
    val uiState: State<ChatUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(apiKey = geminiRepository.getApiKey())
        }
    }

    fun setApiKey(key: String) {
        geminiRepository.setApiKey(key)
        _uiState.value = _uiState.value.copy(apiKey = key, status = Status.SUCCESS)
    }

    fun generateContent(message: String, images: List<ByteArray> = emptyList()) {
        viewModelScope.launch {
            addToMessages(message, images, Sender.User)
            addToMessages("", emptyList(), Sender.Bot, true)
            try {
                val response = geminiRepository.generate(message, images)
                val role = response.candidates?.firstOrNull()?.content?.role.orEmpty()
                val responseText =
                    response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text.orEmpty()

                if (role.isNotEmpty() && responseText.isNotEmpty()) {
                    updateLastBotMessage(responseText, Status.SUCCESS)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                val errorMessage = "An error occurred, please retry."
                updateLastBotMessage(errorMessage, Status.ERROR)
            }
        }
    }

    private fun updateLastBotMessage(text: String, status: Status) {
        val messages = _uiState.value.messages.toMutableList()
        if (messages.isNotEmpty() && messages.last().sender == Sender.Bot) {
            val last = messages.last()
            val updatedMessage = last.copy(text = text, isLoading = status == Status.LOADING)
            messages[messages.lastIndex] = updatedMessage
            _uiState.value = _uiState.value.copy(
                messages = messages,
                status = status
            )
        }
    }

    private fun addToMessages(
        text: String,
        images: List<ByteArray>,
        sender: Sender,
        isLoading: Boolean = false
    ) {
        val message = Message(sender, text, images, isLoading)
        _uiState.value = _uiState.value.copy(
            messages = _uiState.value.messages + message,
            status = if (isLoading) Status.LOADING else Status.IDLE
        )
    }
}