package uz.mobile.footzone.android.presentation.screens.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.mobile.footzone.common.ErrorState
import uz.mobile.footzone.domain.usecase.StadiumUseCase
import uz.mobile.footzone.presentation.main.Location
import uz.mobile.footzone.presentation.schedule.ScheduleScreenUiEvent
import uz.mobile.footzone.presentation.schedule.ScheduleSideEffects
import uz.mobile.footzone.presentation.schedule.ScheduleState

class ScheduleViewModel(
    private val stadiumUseCase: StadiumUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ScheduleState())
    val state: StateFlow<ScheduleState> = _state

    private val _sideEffect = MutableSharedFlow<ScheduleSideEffects>()
    val sideEffect: SharedFlow<ScheduleSideEffects> = _sideEffect

    fun onUiEvent(uiEvent: ScheduleScreenUiEvent) {
        when (uiEvent) {

            is ScheduleScreenUiEvent.Login -> {
                viewModelScope.launch {
                    _sideEffect.emit(ScheduleSideEffects.NavigateToLogin)
                }
            }

            is ScheduleScreenUiEvent.ViewStadium -> {

            }

            is ScheduleScreenUiEvent.NavigateToStadium -> {
                val location = Location(uiEvent.stadium.longitude, uiEvent.stadium.latitude)
                viewModelScope.launch {
                    _sideEffect.emit(ScheduleSideEffects.OpenNavigatorChoose(location))
                }
            }
        }
    }

    fun clearViewModel() {
        viewModelScope.launch {
            _state.value = ScheduleState(

            )
        }
    }

    init {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            stadiumUseCase.getNearbyStadiums()
                .onSuccess { stadiums ->
                    _state.value = ScheduleState(
                        stadiums = stadiums,
                        isLoading = false
                    )
                }
                .onFailure { throwable ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorState = ErrorState(
                            hasError = true,
                            errorMessage = throwable.message.orEmpty()
                        )
                    )
                }
        }
    }
}