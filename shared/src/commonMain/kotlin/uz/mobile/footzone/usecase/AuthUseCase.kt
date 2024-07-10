package uz.mobile.footzone.usecase

import uz.mobile.footzone.model.UserType

interface AuthUseCase {
    suspend fun signUp(
        name: String,
        surname: String,
        phone: String,
        password: String,
        rePassword: String,
        userType: UserType
    ): Result<Boolean>
}