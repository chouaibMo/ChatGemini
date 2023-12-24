package domain

interface GeminiRepository {
    suspend fun generateContent(content: String): Response
    fun getApiKey(): String

    fun setApiKey(apiKey: String)
}
