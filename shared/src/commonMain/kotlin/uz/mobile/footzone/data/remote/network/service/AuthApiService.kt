package uz.mobile.footzone.data.remote.network.service

import uz.mobile.footzone.data.remote.network.request.Requests
import uz.mobile.footzone.data.remote.network.response.Responses

interface AuthApiService {
    suspend fun signUp(user: Requests.User): Result<Responses.AccessToken>
}