package data.repository

import data.network.GeminiService
import domain.model.Status
import domain.repository.GeminiRepository

class GeminiRepositoryImpl : GeminiRepository {

    private val geminiService = GeminiService()

    override suspend fun generate(prompt: String, images: List<ByteArray>): Status {
        return try {
            val response = if (images.isEmpty()) {
                geminiService.generateContent(prompt)
            } else {
                geminiService.generateContentWithMedia(prompt, images)
            }.getText()

            response?.let { Status.Success(it) } ?: Status.Error("An error occurred, please retry.")

        } catch (e: Exception) {
            e.printStackTrace()
            Status.Error("An error occurred, please retry.")
        }
    }

    override fun getApiKey(): String {
        return geminiService.getApiKey()
    }

    override fun setApiKey(key: String) {
        geminiService.setApiKey(key)
    }


}