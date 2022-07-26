package com.batmanapp.data.remote
sealed class Resource<out T> {
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorMessage: String?
    ): Resource<Nothing>()
    class Loading<T> : Resource<T>()
}