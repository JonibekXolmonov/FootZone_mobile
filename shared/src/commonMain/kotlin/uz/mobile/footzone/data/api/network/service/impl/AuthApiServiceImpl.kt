package uz.mobile.footzone.data.api.network.service.impl

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import uz.mobile.footzone.data.api.network.api.KtorApi
import uz.mobile.footzone.data.api.network.handle
import uz.mobile.footzone.data.api.network.request.Requests
import uz.mobile.footzone.data.api.network.response.Responses
import uz.mobile.footzone.data.api.network.service.AuthApiService

class AuthApiServiceImpl(
    private val ktorApi: KtorApi
) : AuthApiService, KtorApi by ktorApi {

    companion object {
        const val REGISTER = "api/auth/register"
    }

    private val httpClient = ktorApi.client

     override suspend fun signUp(user: Requests.User): Result<Responses.AccessToken> {
        return httpClient.handle {
            this.post {
                json()
                apiUrl(REGISTER)
                setBody(
                    user
                )
            }
        }
    }
}