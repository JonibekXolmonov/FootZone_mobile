package uz.mobile.footzone.domain.repository


import uz.mobile.footzone.domain.model.Stadiums

interface StadiumRepository {
    suspend fun fetchStadiums(): Result<Stadiums>
}