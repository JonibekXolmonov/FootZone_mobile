package uz.mobile.footzone.viewmodel.main

import uz.mobile.footzone.model.StadiumUiModel

sealed class MainUiEvent {
    data class OnStadiumSelected(val selectedStadium: StadiumUiModel) : MainUiEvent()
    data object OnLocateMe : MainUiEvent()
    data class OnStadiumSearch(val query: String) : MainUiEvent()
    data object NearbyStadiums : MainUiEvent()
    data object BookmarkStadiums : MainUiEvent()
    data object BookedStadiums : MainUiEvent()
    data class OnLocationPermission(val locationStatus: LocationStatus) : MainUiEvent()
    data object PerformSearch : MainUiEvent()
}

sealed class LocationStatus {
    data object Rejected : LocationStatus()
    data class Granted(val location: Location) : LocationStatus()
    data object NotInquired : LocationStatus()
}

data class Location(
    val latitude: Float,
    val longitude: Float,
    val address: String
)