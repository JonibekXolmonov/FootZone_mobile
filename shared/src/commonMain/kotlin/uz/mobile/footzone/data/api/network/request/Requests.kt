package uz.mobile.footzone.data.api.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class Requests {
    @Serializable
    data class User(
        @SerialName("phone") val phone: String,
        @SerialName("fullName") val fullName: String,
        @SerialName("password") val password: String,
        @SerialName("rePassword") val rePassword: String,
        @SerialName("userType") val userType: Int,
    )
}