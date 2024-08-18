package uz.mobile.footzone.data.remote.network.service

import uz.mobile.footzone.data.remote.network.response.Responses

interface StadiumApiService {

    suspend fun createStadium()
    suspend fun deleteStadium()
    suspend fun fetchStadiums(): Result<Responses.StadiumsResponse>
    suspend fun fetchStadiumById(id: Int)
    suspend fun restoreStadium()
    suspend fun updateStadium()
}