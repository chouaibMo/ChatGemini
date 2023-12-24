package domain

sealed class Status {
    data object IDLE: Status()
    data object LOADING: Status()
    data object SUCCESS: Status()
    data object ERROR : Status()
}