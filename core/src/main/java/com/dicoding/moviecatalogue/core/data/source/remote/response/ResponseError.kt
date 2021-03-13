package com.dicoding.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseError(
    @SerializedName("status_code")
    val code: Int,

    @SerializedName("status_message")
    val message: String,

    val success: Boolean
)
