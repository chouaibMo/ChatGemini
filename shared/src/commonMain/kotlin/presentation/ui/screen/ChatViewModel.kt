package presentation.ui.screen

import androidx.compose.runtime.mutableStateOf
import data.GeminiRepositoryImpl
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.GeminiRepository
import domain.Message
import domain.Resource
import domain.Sender
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val geminiRepository: GeminiRepository = GeminiRepositoryImpl()

    val messages = mutableStateOf(listOf<Message>())
    val status = mutableStateOf<Resource>(Resource.IDLE)

    fun generateContent(message: String) {
        addToMessages(message, Sender.User)
        viewModelScope.launch {
            status.value = Resource.Loading
            addToMessages("", Sender.Bot, true)
            try {
                val response = geminiRepository.generateContent(message)
                val role = response.candidates?.firstOrNull()?.content?.role.orEmpty()
                val responseText =
                    response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text.orEmpty()

                if (role.isNotEmpty() && responseText.isNotEmpty()) {
                    status.value = Resource.Success
                    updateLastBotMessage(responseText, false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                status.value = Resource.Error
                updateLastBotMessage("An error occurred, please retry.", false)
            }
        }
    }

    private fun updateLastBotMessage(text: String, isLoading: Boolean) {
        val lastMessage = messages.value.lastOrNull()
        if (lastMessage != null && lastMessage.sender == Sender.Bot) {
            val updatedMessages = messages.value.toMutableList()
            updatedMessages[updatedMessages.lastIndex] = lastMessage.copy(text = text, isLoading = isLoading)
            messages.value = updatedMessages
        }
    }

    private fun addToMessages(text: String, sender: Sender, isLoading: Boolean = false) {
        val botMessage = Message(sender = sender, text = text, isLoading)
        messages.value = messages.value + botMessage
    }
}