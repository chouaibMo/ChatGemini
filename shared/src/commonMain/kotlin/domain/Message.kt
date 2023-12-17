package domain

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Message(
    val sender: Sender,
    val text: String,
    val isLoading: Boolean = false,
) {
    val time: String
        get() = currentTime()

    val isBotMessage: Boolean
        get() = sender == Sender.Bot

    private fun currentTime(): String {
        val datetime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        return "${datetime.hour}:${datetime.minute}"
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