package com.rodrigues.domain.model.util


data class ErrorResponse(
        val code: String,
        val message: String?,
        val fields: List<ErrorFields>?
)

data class ErrorFields(
        val location: String?,
        val message: String?,
)