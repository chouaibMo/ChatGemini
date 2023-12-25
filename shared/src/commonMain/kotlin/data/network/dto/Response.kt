package data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Response(
    @SerialName("candidates") val candidates: List<Candidate> = emptyList(),
    @SerialName("promptFeedback") val promptFeedback: PromptFeedback? = null,
    @SerialName("error") val error: Error? = null,
) {
    fun getText(): String? =
        candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
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

@Serializable
data class Error(
    @SerialName("code") val code: Int,
    @SerialName("message") val message: String,
    @SerialName("status") val status: String,

    )