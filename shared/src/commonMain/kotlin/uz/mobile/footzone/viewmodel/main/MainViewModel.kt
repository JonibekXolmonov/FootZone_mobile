package uz.mobile.footzone.viewmodel.main

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import uz.mobile.footzone.common.ErrorState
import uz.mobile.footzone.model.StadiumUiModel
import uz.mobile.footzone.usecase.StadiumUseCase
import uz.mobile.footzone.util.CoroutineViewModel

class MainViewModel : CoroutineViewModel(), KoinComponent {

    private val stadiumUseCase: StadiumUseCase by inject()

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

    fun onUiEvent(uiEvent: MainUiEvent) {
        when (uiEvent) {
            MainUiEvent.BookedStadiums -> {
                //
            }

            MainUiEvent.BookmarkStadiums -> {
                //
            }

            MainUiEvent.NearbyStadiums -> {
                //
            }

            MainUiEvent.OnLocateMe -> {
                _state.value = state.value.copy(
                    showLocationPermission = true
                )
            }

            is MainUiEvent.OnStadiumSearch -> {
                _state.value = state.value.copy(
                    searchQuery = uiEvent.query
                )
            }

            is MainUiEvent.OnStadiumSelected -> {
                _state.value = state.value.copy(
                    selectedStadium = uiEvent.selectedStadium
                )
            }

            is MainUiEvent.OnLocationPermission -> {
                when (uiEvent.locationStatus) {
                    is LocationStatus.Granted -> {
                        _state.value = state.value.copy(
                            location = uiEvent.locationStatus.location
                        )
                    }

                    LocationStatus.NotInquired -> {
                        _state.value = state.value.copy(
                            location = null
                        )
                    }

                    LocationStatus.Rejected -> {
                        _state.value = state.value.copy(
                            location = null
                        )
                    }
                }
            }

            MainUiEvent.PerformSearch -> {
                //search request
            }
        }
    }

    fun clearViewModel() {
        coroutineScope.launch {
            _state.value = MainState(
                stadiums = state.value.stadiums,
                userType = state.value.userType,
                searchQuery = state.value.searchQuery,
                location = state.value.location
            )
        }
    }

    fun observe(onChange: (MainState) -> Unit) {
        state.onEach {
            onChange(it)
        }.launchIn(coroutineScope)
    }

    init {
        _state.value = state.value.copy(isLoading = true)
        coroutineScope.launch {
            stadiumUseCase.getNearbyStadiums()
                .onSuccess { stadiums ->
                    _state.value = MainState(
                        stadiums = stadiums,
                        isLoading = false
                    )
                }
                .onFailure { throwable ->
                    _state.value = state.value.copy(
                        isLoading = false,
                        errorState = MainErrorState(
                            errorState = ErrorState(
                                hasError = true,
                                errorMessage = throwable.message.orEmpty()
                            )
                        )
                    )
                }
        }
    }
}