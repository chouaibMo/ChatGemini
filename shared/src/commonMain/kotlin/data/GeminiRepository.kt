package data

import domain.GeminiRepository
import data.dto.Response

class GeminiRepositoryImpl : GeminiRepository {

    private val geminiService = GeminiService()

    override suspend fun generate(prompt: String, images: List<ByteArray>): Response {
        return if(images.isEmpty()) {
            geminiService.generateContent(prompt)
        } else {
            geminiService.generateContentWithMedia(prompt, images)
        }
    }

    override fun getApiKey(): String {
        return geminiService.getApiKey()
    }

    override fun setApiKey(key: String) {
        geminiService.setApiKey(key)
    }


}