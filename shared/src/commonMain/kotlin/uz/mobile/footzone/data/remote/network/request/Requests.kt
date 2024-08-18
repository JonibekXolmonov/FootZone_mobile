package uz.mobile.footzone.data.remote.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class Requests {

    @Serializable
    data class User(
        @SerialName("fullName") val fullName: String,
        @SerialName("password") val password: String,
        @SerialName("phone") val phone: String,
        @SerialName("rePassword") val rePassword: String,
        @SerialName("userType") val userType: Int,
    )
}