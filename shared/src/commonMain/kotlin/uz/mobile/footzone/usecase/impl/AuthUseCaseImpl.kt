package uz.mobile.footzone.usecase.impl

import uz.mobile.footzone.common.Constants.STADIUM_OWNER
import uz.mobile.footzone.common.Constants.USER
import uz.mobile.footzone.data.api.network.request.Requests
import uz.mobile.footzone.model.UserType
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
}