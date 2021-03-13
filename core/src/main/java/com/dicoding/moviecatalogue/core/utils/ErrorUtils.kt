package com.dicoding.moviecatalogue.core.utils

import com.dicoding.moviecatalogue.core.data.source.remote.response.ResponseError
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

fun onResponseError(response: Response<*>, onError: (ResponseError) -> Unit) {
    val errJson = (response.errorBody() as ResponseBody).charStream().readLines()[0]
    val responseError = Gson().fromJson(errJson, ResponseError::class.java)
    onError(responseError)
}

fun onResponseError(error: Throwable, onError: (ResponseError) -> Unit) {
    when (error) {
        is HttpException -> when (error.code()) {
            HttpsURLConnection.HTTP_UNAUTHORIZED,
            HttpsURLConnection.HTTP_NOT_FOUND -> {
                val response = error.response()!!
                onResponseError(response) {
                    onError(it)
                }
            }
            else -> onError(
                ResponseError(
                    error.code(),
                    "Response API Error (${error.code()})",
                    false
                )
            )
        }
        is UnknownHostException -> onError(
            ResponseError(
                0,
                "No Internet Connection",
                false
            )
        )
        else -> onError(
            ResponseError(
                0,
                "Server under maintenance",
                false
            )
        )
    }
}