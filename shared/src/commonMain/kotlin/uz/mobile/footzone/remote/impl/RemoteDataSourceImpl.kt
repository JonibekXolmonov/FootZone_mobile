package uz.mobile.footzone.remote.impl

import uz.mobile.footzone.data.api.network.service.AuthApiService
import uz.mobile.footzone.remote.RemoteDataSource

class RemoteDataSourceImpl(private val apiService: AuthApiService) : RemoteDataSource {
    override suspend fun signUp() = apiService.signUp()
}