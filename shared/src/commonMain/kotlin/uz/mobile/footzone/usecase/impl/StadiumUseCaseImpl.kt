package uz.mobile.footzone.usecase.impl

import uz.mobile.footzone.model.Stadiums
import uz.mobile.footzone.repository.StadiumRepository
import uz.mobile.footzone.usecase.StadiumUseCase

class StadiumUseCaseImpl(private val stadiumRepository: StadiumRepository) : StadiumUseCase {
    override suspend fun getNearbyStadiums(): Result<Stadiums> {
        println("mainState, request")
        return stadiumRepository.fetchStadiums()
    }
}