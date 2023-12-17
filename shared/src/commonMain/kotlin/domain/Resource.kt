package domain

sealed class Resource {
    object IDLE: Resource()
    object Loading: Resource()
    object Success: Resource()
    object Error: Resource()
}