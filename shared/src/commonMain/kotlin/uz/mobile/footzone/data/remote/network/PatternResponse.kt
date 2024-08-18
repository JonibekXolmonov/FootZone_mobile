package uz.mobile.footzone.data.remote.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PatternResponse<out T : Any>(
    @SerialName("data") val data: T? = null,
    @SerialName("message") val message: String? = null,
    @SerialName("dataStatus") val status: Int? = null
)
