package uz.mobile.footzone.domain.repository

import uz.mobile.footzone.data.remote.network.request.Requests
import uz.mobile.footzone.data.remote.network.response.Responses


interface AuthRepository {
    suspend fun signUp(user: Requests.User):Result<Responses.AccessToken>
}