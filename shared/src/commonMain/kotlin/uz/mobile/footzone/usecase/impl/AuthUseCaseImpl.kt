package uz.mobile.footzone.usecase.impl

import uz.mobile.footzone.repository.AuthRepository
import uz.mobile.footzone.usecase.AuthUseCase

class AuthUseCaseImpl(
    private val authRepository: AuthRepository
) : AuthUseCase {

    override suspend fun signUp(): Result<Boolean> {
        return authRepository.signUp().map {
            it.token.isNotEmpty()
        }
    }
}