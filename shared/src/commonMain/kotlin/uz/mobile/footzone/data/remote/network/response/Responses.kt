package uz.mobile.footzone.data.remote.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import uz.mobile.footzone.domain.model.StadiumUiModel

class Responses {

    @Serializable
    data class AccessToken(
        @SerialName("token") val token: String = "",
    )

    @Serializable
    data class StadiumsResponse(
        @SerialName("items") val stadiums: List<Stadium>,
        @SerialName("meta") val paginationData: PaginationData
    ) {
        @Serializable
        data class PaginationData(
            val page: Int,
            val size: Int,
            val totalCount: Int,
            val totalPage: Int,
            val hasPreviousPage: Boolean,
            val hasNextPage: Boolean
        )

        @Serializable
        data class Stadium(
            val id: Int,
            val name: String
        ) {
            fun toUiModel(): StadiumUiModel = StadiumUiModel(id = id, name = name)
        }
    }
}