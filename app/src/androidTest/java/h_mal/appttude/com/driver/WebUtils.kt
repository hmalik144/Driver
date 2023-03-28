package h_mal.appttude.com.driver

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class WebUtils {
    private val okHttpClient by lazy { OkHttpClient() }

    suspend fun <T : Any> post(url: String, body: String): T? {
        val requestBody = body.toRequestBody()
        val request = Request.Builder()
            .post(requestBody)
            .url(url)
            .build()

        return okHttpClient.newCall(request).await()
    }

    suspend fun get(url: String): String? {
        val request: Request = Request.Builder()
            .url(url)
            .build()
        return okHttpClient.newCall(request).await()
    }

    private suspend fun <T> Call.await(): T? {
        val objectMapper = Gson()
        val typeToken: TypeToken<T> = object : TypeToken<T>() {}
        return suspendCancellableCoroutine { continuation ->
            enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    continuation.resume(
                        objectMapper.fromJson(
                            response.body?.string(),
                            typeToken.type
                        )
                    )
                }

                override fun onFailure(call: Call, e: IOException) {
                    // Don't bother with resuming the continuation if it is already cancelled.
                    if (continuation.isCancelled) return
                    continuation.resumeWithException(e)
                }
            })

            continuation.invokeOnCancellation {
                try {
                    cancel()
                } catch (ex: Throwable) {
                    //Ignore cancel exception
                }
            }
        }
    }
}