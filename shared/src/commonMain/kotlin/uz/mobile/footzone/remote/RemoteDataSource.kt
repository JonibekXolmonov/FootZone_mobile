package uz.mobile.footzone.remote

import uz.mobile.footzone.data.api.network.response.Responses

interface RemoteDataSource {
    suspend fun signUp(): Result<Responses.AccessToken>
}