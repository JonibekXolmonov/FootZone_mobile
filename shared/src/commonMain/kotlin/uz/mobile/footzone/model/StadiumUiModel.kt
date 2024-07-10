package uz.mobile.footzone.model

data class StadiumUiModel(
    val id: Int,
    val name: String,
    val latitude: Float = 0f,
    val longitude: Float = 0f,
)

typealias Stadiums = List<StadiumUiModel>