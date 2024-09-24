package uz.mobile.footzone.presentation.schedule

import uz.mobile.footzone.common.ErrorState
import uz.mobile.footzone.domain.model.Stadiums
import uz.mobile.footzone.domain.model.UserType

data class ScheduleState(
    val userType: UserType = UserType.UNAUTHORIZED,
    val stadiums: Stadiums = emptyList(),
    val errorState: ErrorState = ErrorState(),
    val isLoading: Boolean = false,
) {
    constructor() : this(
        userType = UserType.USER,
        errorState = ErrorState(),
        isLoading = false,
    )
}