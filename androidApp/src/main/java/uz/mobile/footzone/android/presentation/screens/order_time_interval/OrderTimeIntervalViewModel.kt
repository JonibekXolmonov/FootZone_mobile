package uz.mobile.footzone.android.presentation.screens.order_time_interval

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.mobile.footzone.presentation.order_time_interval.OrderTimeIntervalSideEffects
import uz.mobile.footzone.presentation.order_time_interval.OrderTimeIntervalState
import uz.mobile.footzone.presentation.order_time_interval.OrderTimeIntervalUiEvent

class OrderTimeIntervalViewModel (
): ViewModel() {
    private val _state = MutableStateFlow(OrderTimeIntervalState())
    val state: StateFlow<OrderTimeIntervalState> = _state

    private val _sideEffect = MutableSharedFlow<OrderTimeIntervalSideEffects>()
    val sideEffect: SharedFlow<OrderTimeIntervalSideEffects> = _sideEffect

    fun onUiEvent(uiEvent: OrderTimeIntervalUiEvent) {
        when (uiEvent) {


            else -> {}
        }
    }


    init {
        viewModelScope.launch {
            _state.value = _state.value.copy()
        }
    }
}