package domain.model
sealed class Status {
    data object Idle : Status()
    class Success(val data: String) : Status()
    class Error(val message: String) : Status()
    data object Loading : Status()
}