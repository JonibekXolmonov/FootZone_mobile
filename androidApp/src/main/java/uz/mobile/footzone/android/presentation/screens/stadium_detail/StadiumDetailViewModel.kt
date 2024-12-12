package uz.mobile.footzone.android.presentation.screens.stadium_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.mobile.footzone.presentation.main.Location
import uz.mobile.footzone.presentation.stadium_detail.StadiumDetailSideEffects
import uz.mobile.footzone.presentation.stadium_detail.StadiumDetailState
import uz.mobile.footzone.presentation.stadium_detail.StadiumDetailUiEvent

class StadiumDetailViewModel (
) : ViewModel() {
    private val _state = MutableStateFlow(StadiumDetailState())
    val state: StateFlow<StadiumDetailState> = _state

    private val _sideEffect = MutableSharedFlow<StadiumDetailSideEffects>()
    val sideEffect: SharedFlow<StadiumDetailSideEffects> = _sideEffect

    fun onUiEvent(uiEvent: StadiumDetailUiEvent) {
        when (uiEvent) {

            is StadiumDetailUiEvent.NavigateToStadium -> {
                val location = Location(uiEvent.stadium.longitude, uiEvent.stadium.latitude)
                viewModelScope.launch {
                    _sideEffect.emit(StadiumDetailSideEffects.OpenNavigatorChoose(location))
                }
            }

            is StadiumDetailUiEvent.ShareStadium -> {
                viewModelScope.launch {
                    _sideEffect.emit(StadiumDetailSideEffects.OpenShareChoose(uiEvent.stadium))
                }
            }

            is StadiumDetailUiEvent.SelectDate -> {
                viewModelScope.launch {
                    _sideEffect.emit(StadiumDetailSideEffects.OpenCalendarAlert)
                }
            }

            else -> {}
        }
    }


    init {
        viewModelScope.launch {
            _state.value = _state.value.copy()
        }
    }
}