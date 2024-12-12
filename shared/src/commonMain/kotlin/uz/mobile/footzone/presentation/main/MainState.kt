package uz.mobile.footzone.presentation.main

import uz.mobile.footzone.common.Constants.EMPTY
import uz.mobile.footzone.common.Constants.LOADING
import uz.mobile.footzone.common.ErrorState
import uz.mobile.footzone.domain.model.StadiumUiModel
import uz.mobile.footzone.domain.model.Stadiums
import uz.mobile.footzone.domain.model.UserType

data class MainState(
    val stadiums: Stadiums = emptyList(),
    val filteredStadiums: Stadiums = emptyList(),
    val selectedStadium: StadiumUiModel? = null,
    val userType: UserType = UserType.USER,
    val searchQuery: String = EMPTY,
    val userLocation: UserLocation = UserLocation(),
    val errorState: ErrorState = ErrorState(),
    val isLoading: Boolean = false,
    val currentlyOpenFilterActive: Boolean = false,
    val wellRatedFilterActive: Boolean = false,
    val sheetState: SheetState = SheetState.HalfExpanded,
    val listSheetState: SheetState = SheetState.Hidden,
) {
    constructor() : this(
        stadiums = emptyList(),
        filteredStadiums = emptyList(),
        selectedStadium = null,
        userType = UserType.USER,
        searchQuery = EMPTY,
        userLocation = UserLocation(),
        errorState = ErrorState(),
        isLoading = false,
        currentlyOpenFilterActive = false,
        wellRatedFilterActive = false,
        sheetState = SheetState.PartiallyExpanded,
        listSheetState = SheetState.Hidden
    )
}

enum class SheetState(val value: Float) {
    FullExpanded(1f),
    HalfExpanded(0.7f),
    PartiallyExpanded(0.1f),
    Hidden(0f)
}

data class UserLocation(
    val userAddress: String = LOADING,
    val point: Location? = null
)

data class Location(
    val longitude: Double,
    val latitude: Double
)

enum class BottomSheetAction {
    NearStadiums,
    SavedStadiums,
    PreviouslyBookedStadiums,
    MyStadiums
}