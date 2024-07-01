package uz.mobile.footzone.usecase

interface AuthUseCase {
    suspend fun signUp(
        name: String,
        surname: String,
        phone: String,
        password: String,
        rePassword: String,
        userType: Int = 0
    ): Result<Boolean>
}