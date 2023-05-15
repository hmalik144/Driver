package h_mal.appttude.com.driver.firebase.api

import h_mal.appttude.com.driver.firebase.model.*
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.PUT

interface FirebaseApi {

    @PUT("v1/accounts:signUp")
    suspend fun signUp(@Body request: SignUpRequest): Response<SignUpResponse>

    @PUT("v1/accounts:signInWithPassword")
    suspend fun signInWithPassword(@Body request: SignUpRequest): Response<SignUpResponse>

    @PUT("v1/accounts:sendOobCode")
    suspend fun sendOobCode(@Body request: Map<String, String>): Response<OobCodeResponse>

    @PUT("v1/accounts:resetPassword")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<ResetPasswordResponse>

    // invoke method creating an invocation of the api call
    companion object {
        operator fun invoke(): FirebaseApi {
            val host = "10.0.2.2"
            val baseUrl = "http://$host:9099/identitytoolkit.googleapis.com/"

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor {
                    val original = it.request()
                    val url = original.url.newBuilder()
                        .addQueryParameter("key", "apikeydfasdfasdfasdf")
                        .build()

                    val requestBuilder = original.newBuilder().url(url)
                    val request: Request = requestBuilder.build()
                    it.proceed(request)
                }
                .build()

            // creation of retrofit class
            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FirebaseApi::class.java)
        }
    }
}