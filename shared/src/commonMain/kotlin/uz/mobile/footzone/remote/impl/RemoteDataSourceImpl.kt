package uz.mobile.footzone.remote.impl

import uz.mobile.footzone.data.api.network.request.Requests
import uz.mobile.footzone.data.api.network.response.Responses
import uz.mobile.footzone.data.api.network.service.AuthApiService
import uz.mobile.footzone.data.api.network.service.StadiumApiService
import uz.mobile.footzone.remote.RemoteDataSource

class RemoteDataSourceImpl(private val apiService: AuthApiService,private var stadiumApiService: StadiumApiService) : RemoteDataSource {

    override suspend fun signUp(user: Requests.User): Result<Responses.AccessToken> = apiService.signUp(user)

    override suspend fun fetchStadiums(): Result<Responses.StadiumsResponse> = stadiumApiService.fetchStadiums()

}