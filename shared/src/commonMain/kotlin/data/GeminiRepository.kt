package data

import domain.GeminiRepository
import domain.Response

class GeminiRepositoryImpl : GeminiRepository {
    override suspend fun generateContent(content: String): Response {
        return GeminiService.generateContent(content)
    }
}