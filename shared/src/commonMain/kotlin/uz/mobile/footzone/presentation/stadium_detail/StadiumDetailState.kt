package uz.mobile.footzone.presentation.stadium_detail

import uz.mobile.footzone.common.ErrorState

data class StadiumDetailState (
    val errorState: ErrorState = ErrorState(),
    val isLoading: Boolean = false,
) {
    constructor() : this(
        errorState = ErrorState(),
        isLoading = false,
    )
}