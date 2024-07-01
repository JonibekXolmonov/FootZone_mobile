package uz.mobile.footzone.usecase.impl

import uz.mobile.footzone.data.api.network.request.Requests
import uz.mobile.footzone.repository.AuthRepository
import uz.mobile.footzone.usecase.AuthUseCase

class AuthUseCaseImpl(
    private val authRepository: AuthRepository
) : AuthUseCase {

    override suspend fun signUp(
        name: String,
        surname: String,
        phone: String,
        password: String,
        rePassword: String,
        userType: Int
    ): Result<Boolean> {
        val user = Requests.User(
            fullName = "$name $surname",
            phone = phone,
            password = password,
            rePassword = rePassword,
            userType = userType
        )
        return authRepository.signUp(user).map {
            it.token.isNotEmpty()
        }
    }
}