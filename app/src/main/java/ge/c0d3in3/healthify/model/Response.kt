package ge.c0d3in3.healthify.model

sealed class Response<out T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Fail(val message: String): Response<Nothing>()
}