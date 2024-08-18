package uz.mobile.footzone.domain.usecase

import uz.mobile.footzone.domain.model.Stadiums

interface StadiumUseCase {
    suspend fun getNearbyStadiums(): Result<Stadiums>
}