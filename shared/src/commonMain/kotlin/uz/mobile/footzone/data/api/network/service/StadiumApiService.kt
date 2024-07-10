package uz.mobile.footzone.data.api.network.service

import uz.mobile.footzone.data.api.network.response.Responses

interface StadiumApiService {

    suspend fun createStadium()
    suspend fun deleteStadium()
    suspend fun fetchStadiums(): Result<Responses.StadiumsResponse>
    suspend fun fetchStadiumById(id: Int)
    suspend fun restoreStadium()
    suspend fun updateStadium()
}