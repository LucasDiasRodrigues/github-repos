package com.rodrigues.domain.model.util

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    IDLE,
    NETWORK_ERROR,
}

data class Request<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null,
    val error: ErrorResponse? = null
) {
    companion object {
        fun <T> success(data: T): Request<T> = Request(status = Status.SUCCESS, data = data)
        fun <T> error(message: String? = null, errorResponse: ErrorResponse? = null): Request<T> =
            Request(status = Status.ERROR, message = message, error = errorResponse)
        fun <T> networkError(): Request<T> = Request(status = Status.NETWORK_ERROR)
        fun <T> loading(): Request<T> = Request(status = Status.LOADING)
        fun <T> idle(): Request<T> = Request(status = Status.IDLE)
    }
}
