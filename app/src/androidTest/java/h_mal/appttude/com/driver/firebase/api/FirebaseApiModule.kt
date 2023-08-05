package h_mal.appttude.com.driver.firebase.api

import h_mal.appttude.com.driver.firebase.model.SignUpRequest
import h_mal.appttude.com.driver.firebase.model.SignUpResponse
import kotlinx.coroutines.runBlocking

class FirebaseApiModule {

    private val firebaseApi = FirebaseApi()

    fun signUp(email: String, password: String): SignUpResponse? {
        return runBlocking {
            val req = SignUpRequest(email = email, password = password)
            val response = firebaseApi.signUp(req)
            response.body()
        }
    }

    fun signIn(email: String, password: String): SignUpResponse? {
        return runBlocking {
            val req = SignUpRequest(email = email, password = password)
            val response = firebaseApi.signInWithPassword(req)
            response.body()
        }
    }
}