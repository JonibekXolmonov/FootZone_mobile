package uz.mobile.footzone.viewmodel.main

import uz.mobile.footzone.common.Constants.EMPTY
import uz.mobile.footzone.common.ErrorState
import uz.mobile.footzone.model.StadiumUiModel
import uz.mobile.footzone.model.Stadiums
import uz.mobile.footzone.model.UserType

data class MainState(
    val stadiums: Stadiums = emptyList(),
    val selectedStadium: StadiumUiModel? = null,
    val userType: UserType = UserType.USER,
    val searchQuery: String = EMPTY,
    val userAddress: String = EMPTY,
    val location: Location? = null,
    val errorState: MainErrorState = MainErrorState(),
    val isLoading: Boolean = false,
    val showLocationPermission: Boolean = false
) {
    constructor() : this(
        stadiums = emptyList(),
        selectedStadium = null,
        userType = UserType.USER,
        searchQuery = EMPTY,
        location = null,
        userAddress = EMPTY,
        errorState = MainErrorState(),
        isLoading = false,
        showLocationPermission = false
    )
}

data class MainErrorState(
    val errorState: ErrorState = ErrorState(),
) {
    constructor() : this(
        errorState = ErrorState()
    )
}