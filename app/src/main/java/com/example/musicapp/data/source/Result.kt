package com.example.musicapp.data.source

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val error: Exception) : Result<T>()
}