package uz.mobile.footzone.domain.usecase.impl

import uz.mobile.footzone.common.Constants.STADIUM_OWNER
import uz.mobile.footzone.common.Constants.USER
import uz.mobile.footzone.data.remote.network.request.Requests
import uz.mobile.footzone.domain.model.UserType
import uz.mobile.footzone.domain.repository.AuthRepository
import uz.mobile.footzone.domain.usecase.AuthUseCase

class AuthUseCaseImpl(
    private val authRepository: AuthRepository
) : AuthUseCase {

    override suspend fun signUp(
        name: String,
        surname: String,
        phone: String,
        password: String,
        rePassword: String,
        userType: UserType
    ): Result<Boolean> {
        val user = Requests.User(
            fullName = "$name $surname",
            phone = "998${phone.takeLast(9)}",
            password = password,
            rePassword = rePassword,
            userType = if (userType == UserType.USER) USER else STADIUM_OWNER
        )
        return authRepository.signUp(user).map {
            it.token.isNotEmpty()
        }
    }

    override suspend fun login(phone: String, password: String) {

    }

    override suspend fun verifyOTP(phone: String, otp: String) {

    }

    override suspend fun sendOTP(phone: String) {

    }

    override suspend fun resetPassword() {

    }
}