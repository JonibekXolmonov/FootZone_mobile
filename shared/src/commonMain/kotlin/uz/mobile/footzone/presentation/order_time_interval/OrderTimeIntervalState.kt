package uz.mobile.footzone.presentation.order_time_interval

import uz.mobile.footzone.common.ErrorState

data class OrderTimeIntervalState (
    val errorState: ErrorState = ErrorState(),
    val isLoading: Boolean = false,
) {
    constructor() : this(
        errorState = ErrorState(),
        isLoading = false,
    )
}
