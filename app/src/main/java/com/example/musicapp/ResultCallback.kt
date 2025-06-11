package com.example.musicapp

interface ResultCallback<T> {
    fun onResult(result: T)
}