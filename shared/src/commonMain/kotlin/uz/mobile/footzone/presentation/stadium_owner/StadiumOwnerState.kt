package uz.mobile.footzone.presentation.stadium_owner

import uz.mobile.footzone.common.ErrorState

data class StadiumOwnerState (
    val errorState: ErrorState = ErrorState(),
    val isLoading: Boolean = false,
) {
    constructor() : this(
        errorState = ErrorState(),
        isLoading = false,
    )
}