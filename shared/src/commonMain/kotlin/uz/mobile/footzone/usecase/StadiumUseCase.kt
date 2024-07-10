package uz.mobile.footzone.usecase

import uz.mobile.footzone.model.Stadiums

interface StadiumUseCase {
    suspend fun getNearbyStadiums(): Result<Stadiums>
}