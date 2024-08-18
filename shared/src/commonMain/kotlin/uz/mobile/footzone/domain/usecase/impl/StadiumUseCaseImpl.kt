package uz.mobile.footzone.domain.usecase.impl

import uz.mobile.footzone.domain.model.StadiumUiModel
import uz.mobile.footzone.domain.model.Stadiums
import uz.mobile.footzone.domain.repository.StadiumRepository
import uz.mobile.footzone.domain.usecase.StadiumUseCase

class StadiumUseCaseImpl(private val stadiumRepository: StadiumRepository) : StadiumUseCase {
    override suspend fun getNearbyStadiums(): Result<Stadiums> {
//        return stadiumRepository.fetchStadiums()
        return Result.success(
            listOf(
                StadiumUiModel(
                    id = 1,
                    name = "demo 1",
                    69.2401,
                    41.2995,
                    isSaved = true,
                    wasPreviouslyBooked = true,
                    rating = 3.0
                ),
                StadiumUiModel(
                    id = 2,
                    name = "demo 2",
                    69.2451,
                    41.2895,
                    wasPreviouslyBooked = true,
                    isOpen = true,
                    rating = 3.0
                ),
                StadiumUiModel(
                    id = 3,
                    name = "demo 3",
                    69.2421,
                    41.2965,
                    rating = 2.0
                ),
                StadiumUiModel(
                    id = 4,
                    name = "demo 4",
                    69.2441,
                    41.2935,
                    isSaved = true,
                    wasPreviouslyBooked = true,
                    rating = 5.0,
                    isOpen = true
                ),
                StadiumUiModel(
                    id = 5,
                    name = "demo 5",
                    69.2471,
                    41.2905,
                    wasPreviouslyBooked = true
                ),
            )
        )
    }
}