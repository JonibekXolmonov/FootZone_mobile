package uz.mobile.footzone.data.api.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class Responses {

    @Serializable
    data class AccessToken(
        @SerialName("token") val token: String = "",
    )
}