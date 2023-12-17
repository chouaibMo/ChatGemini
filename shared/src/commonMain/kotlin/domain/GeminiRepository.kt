package domain

interface GeminiRepository {
    suspend fun generateContent(content: String): Response
}
