package uz.mobile.footzone.data.remote

import uz.mobile.footzone.data.remote.network.request.Requests
import uz.mobile.footzone.data.remote.network.response.Responses

interface RemoteDataSource {
    suspend fun signUp(user: Requests.User): Result<Responses.AccessToken>
    suspend fun fetchStadiums():Result<Responses.StadiumsResponse>
}