package uz.mobile.footzone.domain.model

import uz.mobile.footzone.presentation.main.UserLocation

data class StadiumUiModel(
    val id: Int,
    val name: String,
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val isSaved: Boolean = false,
    val wasPreviouslyBooked: Boolean = false,
    val rating: Double = 4.0,
    val isOpen: Boolean = false
) {

    fun isNear(userLocation: UserLocation): Boolean {
        return true
    }
}

typealias Stadiums = List<StadiumUiModel>