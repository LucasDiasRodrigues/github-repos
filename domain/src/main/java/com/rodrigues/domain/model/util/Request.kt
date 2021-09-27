package com.rodrigues.domain.model.util

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    IDLE,
}

data class Request<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null,
    val error: ErrorResponseBody? = null
) {
    companion object {
        fun <T> success(data: T): Request<T> = Request(status = Status.SUCCESS, data = data)
        fun <T> error(message: String? = null, errorResponse: ErrorResponseBody? = null): Request<T> =
            Request(status = Status.ERROR, message = message, error = errorResponse)
        fun <T> loading(): Request<T> = Request(status = Status.LOADING)
        fun <T> idle(): Request<T> = Request(status = Status.IDLE)
    }
}
