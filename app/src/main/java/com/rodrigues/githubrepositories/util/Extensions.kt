package com.rodrigues.githubrepositories.util

import com.google.gson.Gson
import com.rodrigues.domain.model.util.ErrorResponseBody
import retrofit2.HttpException

fun HttpException.getErrorResponseBody(): ErrorResponseBody? {
    return try {
        val charStream = response()?.errorBody()?.charStream()
        Gson().fromJson(charStream, ErrorResponseBody::class.java)
    } catch (_: Exception) {
        null
    }
}