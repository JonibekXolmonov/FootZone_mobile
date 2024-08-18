package uz.mobile.footzone.data.remote.network.service.impl

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.parameters
import uz.mobile.footzone.data.remote.network.api.KtorApi
import uz.mobile.footzone.data.remote.network.handle
import uz.mobile.footzone.data.remote.network.response.Responses
import uz.mobile.footzone.data.remote.network.service.StadiumApiService
import uz.mobile.footzone.data.remote.network.service.impl.AuthApiServiceImpl.Companion

class StadiumApiServiceImpl(
    private val ktorApi: KtorApi
) : StadiumApiService, KtorApi by ktorApi {

    companion object {
        const val STADIUMS = "api/stadium/get"
    }

    private val httpClient = ktorApi.client

    override suspend fun createStadium() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteStadium() {
        TODO("Not yet implemented")
    }

    override suspend fun fetchStadiums(): Result<Responses.StadiumsResponse> {
        return httpClient.handle {
            this.post {
                json()
                apiUrl(STADIUMS)
                parameters {
                    append("page", "1")
                    append("size", "10")
                }
            }
        }
    }

    override suspend fun fetchStadiumById(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun restoreStadium() {
        TODO("Not yet implemented")
    }

    override suspend fun updateStadium() {
        TODO("Not yet implemented")
    }

}