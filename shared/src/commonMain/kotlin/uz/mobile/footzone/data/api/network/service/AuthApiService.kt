package uz.mobile.footzone.data.api.network.service

import uz.mobile.footzone.data.api.network.response.Responses

interface AuthApiService {
    suspend fun signUp(): Result<Responses.AccessToken>
}