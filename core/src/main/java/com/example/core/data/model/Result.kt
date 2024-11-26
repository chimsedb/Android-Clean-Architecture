package com.example.core.data.model

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure<T>(val error: Throwable) : Result<T>()

    companion object {
        fun <T> process(body: () -> T): Result<T> = try {
            Success(data = body())
        } catch (e: Throwable) {
            Failure(error = e)
        }
    }

    fun get(): T? = when (this) {
        is Success -> this.data
        is Failure -> null
    }

    fun getOrDefault(default: T): T = when (this) {
        is Success -> this.data
        is Failure -> default
    }

    fun <R> map(transform: (T) -> R): Result<R> = when (this) {
        is Success -> Success(data = transform(this.data))
        is Failure -> Failure(error = error)
    }

}

