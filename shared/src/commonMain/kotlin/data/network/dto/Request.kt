package data.network.dto

import io.ktor.util.encodeBase64
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Request(
    @SerialName("contents") private val contents: List<RequestContent>
) {
    class RequestBuilder {

        private val parts = mutableListOf<RequestPart>()

        private fun addPart(part: RequestPart) {
            parts.add(part)
        }

        fun addText(text: String): RequestBuilder {
            addPart(RequestPart(text = text))
            return this
        }

        fun addImage(image: ByteArray, mimeType: String = "image/png"): RequestBuilder {
            val imagePart = RequestPart(requestInlineData = RequestInlineData(mimeType, image.encodeBase64()))
            addPart(imagePart)
            return this
        }

        fun addImages(images: List<ByteArray>, mimeType: String = "image/png"): RequestBuilder {
            images.forEach {
                val part = RequestPart(requestInlineData = RequestInlineData(mimeType, it.encodeBase64()))
                addPart(part)
            }
            return this
        }

        fun build(role: String? = null): Request {
            return Request(
                contents = listOf(
                    RequestContent(parts = parts, role = role)
                )
            )
        }
    }
}

@Serializable
data class RequestContent(
    @SerialName("parts") val parts: List<RequestPart> = emptyList(),
    @SerialName("role") val role: String? = null,
)

@Serializable
data class RequestPart(
    @SerialName("text") val text: String? = null,
    @SerialName("inlineData") val requestInlineData: RequestInlineData? = null,
)

@Serializable
data class RequestInlineData(
    @SerialName("mimeType") val mimeType: String,
    @SerialName("data") val data: String,
)