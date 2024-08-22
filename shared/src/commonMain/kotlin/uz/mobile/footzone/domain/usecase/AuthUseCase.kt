package uz.mobile.footzone.domain.usecase

import uz.mobile.footzone.domain.model.UserType

interface AuthUseCase {
    suspend fun signUp(
        name: String,
        surname: String,
        phone: String,
        password: String,
        rePassword: String,
        userType: UserType
    ): Result<Boolean>

    suspend fun login(phone: String, password: String)

    suspend fun verifyOTP(phone: String, otp: String)

    suspend fun sendOTP(phone: String)

    suspend fun resetPassword()

    suspend fun isAuthorised(): Boolean
}