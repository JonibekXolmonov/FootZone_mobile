package uz.mobile.footzone.usecase

interface AuthUseCase {
    suspend fun signUp(): Result<Boolean>
}