package uz.mobile.footzone.repository


import uz.mobile.footzone.model.Stadiums

interface StadiumRepository {
    suspend fun fetchStadiums(): Result<Stadiums>
}