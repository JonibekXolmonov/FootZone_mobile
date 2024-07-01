package uz.mobile.footzone.repository

import uz.mobile.footzone.data.api.network.request.Requests
import uz.mobile.footzone.data.api.network.response.Responses


interface AuthRepository {
    suspend fun signUp(user: Requests.User):Result<Responses.AccessToken>
}