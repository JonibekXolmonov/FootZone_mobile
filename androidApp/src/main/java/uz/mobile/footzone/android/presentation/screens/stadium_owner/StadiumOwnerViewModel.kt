package uz.mobile.footzone.android.presentation.screens.stadium_owner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.mobile.footzone.presentation.stadium_owner.StadiumOwnerSideEffects
import uz.mobile.footzone.presentation.stadium_owner.StadiumOwnerState
import uz.mobile.footzone.presentation.stadium_owner.StadiumOwnerUiEvent

class StadiumOwnerViewModel : ViewModel() {
    private val _state = MutableStateFlow(StadiumOwnerState())
    val state: StateFlow<StadiumOwnerState> = _state

    private val _sideEffect = MutableSharedFlow<StadiumOwnerSideEffects>()
    val sideEffect: SharedFlow<StadiumOwnerSideEffects> = _sideEffect

    fun onUiEvent(uiEvent: StadiumOwnerUiEvent) {
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