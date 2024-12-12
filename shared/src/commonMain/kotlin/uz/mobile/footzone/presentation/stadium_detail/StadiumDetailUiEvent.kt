package uz.mobile.footzone.presentation.stadium_detail

import uz.mobile.footzone.domain.model.StadiumUiModel
import uz.mobile.footzone.presentation.main.Location


sealed class StadiumDetailUiEvent {
    data class BookStadium(val stadium: StadiumUiModel) : StadiumDetailUiEvent()
    data class NavigateToStadium(val stadium: StadiumUiModel) : StadiumDetailUiEvent()
    data class SaveStadium(val stadium: StadiumUiModel) : StadiumDetailUiEvent()
    data class ShareStadium(val stadium: StadiumUiModel) : StadiumDetailUiEvent()
    data object SelectDate : StadiumDetailUiEvent()
}

sealed class StadiumDetailSideEffects {
    data class OpenNavigatorChoose(val stadiumLocation: Location) : StadiumDetailSideEffects()
    data class OpenShareChoose(val stadium: StadiumUiModel) : StadiumDetailSideEffects()
    data object OpenCalendarAlert : StadiumDetailSideEffects()
    data object Nothing : StadiumDetailSideEffects()
}