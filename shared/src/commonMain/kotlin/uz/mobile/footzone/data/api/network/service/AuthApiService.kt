package uz.mobile.footzone.data.api.network.service

import uz.mobile.footzone.data.api.network.request.Requests
import uz.mobile.footzone.data.api.network.response.Responses

interface AuthApiService {
    suspend fun signUp(user: Requests.User): Result<Responses.AccessToken>
}