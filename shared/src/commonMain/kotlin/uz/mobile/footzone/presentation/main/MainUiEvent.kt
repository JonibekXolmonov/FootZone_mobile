package uz.mobile.footzone.presentation.main

import uz.mobile.footzone.domain.model.StadiumUiModel

sealed class MainScreenUiEvent {
    data class OnStadiumSelected(val selectedStadium: StadiumUiModel) : MainScreenUiEvent()
    data class SearchQueryChanged(val query: String) : MainScreenUiEvent()
    data object PerformSearch : MainScreenUiEvent()

    data class BookStadium(val stadium: StadiumUiModel) : MainScreenUiEvent()
    data class NavigateToStadium(val stadium: StadiumUiModel) : MainScreenUiEvent()
    data class SaveStadium(val stadium: StadiumUiModel) : MainScreenUiEvent()

    data object CurrentlyOpenFilter : MainScreenUiEvent()
    data object WellRatedFilter : MainScreenUiEvent()

    data class BottomSheetActionPerformed(val bottomSheetAction: BottomSheetAction) :
        MainScreenUiEvent()

    data class BottomSheetStateChange(val sheetState: SheetState) :
        MainScreenUiEvent()

    data class ListBottomSheetStateChange(val listSheetState: SheetState) :
        MainScreenUiEvent()

    data object Notification : MainScreenUiEvent()
    data object OnBackPressed : MainScreenUiEvent()
    data object RequestLocationPermission : MainScreenUiEvent()
    data object DismissDialog : MainScreenUiEvent()
}

sealed class MainScreenSideEffects {
    data object NavigateToNotifications : MainScreenSideEffects()
    data object NavigateToOwnerStadiums : MainScreenSideEffects()
    data class OpenNavigatorChoose(val stadiumLocation: Location) : MainScreenSideEffects()
    data object OpenGPSettings : MainScreenSideEffects()
    data object UnAuthorisedUser : MainScreenSideEffects()
    data object Nothing : MainScreenSideEffects()
    data class OpenStadiumDetail(val stadiumId: Int) : MainScreenSideEffects()
}