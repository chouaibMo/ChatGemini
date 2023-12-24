package domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Message(
    val sender: Sender,
    val text: String,
    val images: List<ByteArray> = emptyList(),
    val isLoading: Boolean = false,
) {
    val time: String
        get() = currentTime()

    val isBotMessage: Boolean
        get() = sender == Sender.Bot

    private fun currentTime(): String {
        val datetime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val hour = if (datetime.hour < 10) "0${datetime.hour}" else datetime.hour
        val minute = if (datetime.minute < 10) "0${datetime.minute}" else datetime.minute
        return "${hour}:${minute}"
    }
}

enum class Sender {
    User,
    Bot;

    override fun toString(): String {
        return when (this) {
            User -> "You"
            Bot -> "ChatGemini"
        }
    }
}