package uz.mobile.footzone.repository

import uz.mobile.footzone.data.api.network.response.Responses


interface AuthRepository {
    suspend fun signUp():Result<Responses.AccessToken>
}