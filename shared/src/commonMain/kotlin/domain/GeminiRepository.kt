package domain

import data.dto.Response

interface GeminiRepository {
    suspend fun generate(prompt: String, images: List<ByteArray> = emptyList()): Response

    fun getApiKey(): String

    fun setApiKey(key: String)
}
