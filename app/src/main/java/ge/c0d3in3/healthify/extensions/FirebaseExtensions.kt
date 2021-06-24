package ge.c0d3in3.healthify.extensions

import com.google.android.gms.tasks.Task
import ge.c0d3in3.healthify.model.Response
import kotlinx.coroutines.tasks.await

suspend fun <T : Any> Task<T>.execute(): Response<T> {
    return try {
        Response.Success(this.await())
    } catch (e: Exception) {
        Response.Fail(e.message?: "")
    }
}

suspend fun <T: Any> Response<T>.go(onSuccess: suspend (T) -> Unit, onFail: suspend (String) -> Unit) {
    when(this) {
        is Response.Success -> onSuccess.invoke(data)
        is Response.Fail -> onFail.invoke(message)
    }
}