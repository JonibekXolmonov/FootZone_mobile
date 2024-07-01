package uz.mobile.footzone.remote

import uz.mobile.footzone.data.api.network.request.Requests
import uz.mobile.footzone.data.api.network.response.Responses

interface RemoteDataSource {
    suspend fun signUp(user: Requests.User): Result<Responses.AccessToken>
}