package uz.mobile.footzone.presentation.order_time_interval

sealed class OrderTimeIntervalUiEvent {
}

sealed class OrderTimeIntervalSideEffects {

    data object Nothing : OrderTimeIntervalSideEffects()
}