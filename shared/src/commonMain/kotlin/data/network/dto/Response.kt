package data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Response(
    @SerialName("candidates") val candidates: List<Candidate>,
    @SerialName("promptFeedback") val promptFeedback: PromptFeedback? = null,
) {
    fun getText(): String? {
        if (candidates.isEmpty()) {
            println("No candidates were found, but was asked to get a candidate.")
            return null
        }

        val parts = candidates.first().content?.parts

        return parts?.let {
            println("Returning first part of ${it.size} parts.")
            it.firstOrNull()?.text
        } ?: run {
            println("No parts were found, but was asked to get a candidate.")
            null
        }
    }
}

@Serializable
data class Candidate(
    @SerialName("index") val index: Int,
    @SerialName("content") val content: Content? = null,
    @SerialName("finishReason") val finishReason: String? = null,
    @SerialName("safetyRatings") val safetyRatings: List<SafetyRating> = emptyList()
)

@Serializable
data class Content(
    @SerialName("parts") val parts: List<Part> = emptyList(),
    @SerialName("role") val role: String? = null,
)

@Serializable
data class Part(
    @SerialName("text") val text: String? = null,
)

@Serializable
data class PromptFeedback(
    @SerialName("safetyRatings") val safetyRatings: List<SafetyRating> = emptyList()
)

@Serializable
data class SafetyRating(
    @SerialName("category") val category: String,
    @SerialName("probability") val probability: String
)