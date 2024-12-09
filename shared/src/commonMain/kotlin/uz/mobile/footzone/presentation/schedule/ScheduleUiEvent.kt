package uz.mobile.footzone.presentation.schedule

import uz.mobile.footzone.domain.model.StadiumUiModel
import uz.mobile.footzone.presentation.main.Location
import uz.mobile.footzone.presentation.main.MainScreenSideEffects


sealed class ScheduleScreenUiEvent {
    data object Login : ScheduleScreenUiEvent()
    data class NavigateToStadium(val stadium: StadiumUiModel) : ScheduleScreenUiEvent()
    data class ViewStadium(val stadium: StadiumUiModel) : ScheduleScreenUiEvent()

}

sealed class ScheduleSideEffects {
    data object NavigateToLogin : ScheduleSideEffects()
    data class OpenNavigatorChoose(val stadiumLocation: Location) : ScheduleSideEffects()
    data object Nothing : ScheduleSideEffects()
}