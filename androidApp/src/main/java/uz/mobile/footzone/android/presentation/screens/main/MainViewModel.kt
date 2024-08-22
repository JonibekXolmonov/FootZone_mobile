package uz.mobile.footzone.android.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.mobile.footzone.android.data.LocationClient
import uz.mobile.footzone.common.Constants.LOCATION_UPDATE_INTERVAl
import uz.mobile.footzone.common.ErrorState
import uz.mobile.footzone.domain.model.UserType
import uz.mobile.footzone.domain.usecase.StadiumUseCase
import uz.mobile.footzone.presentation.main.BottomSheetAction
import uz.mobile.footzone.presentation.main.Location
import uz.mobile.footzone.presentation.main.MainScreenSideEffects
import uz.mobile.footzone.presentation.main.MainScreenUiEvent
import uz.mobile.footzone.presentation.main.MainState
import uz.mobile.footzone.presentation.main.SheetState
import uz.mobile.footzone.presentation.main.UserLocation
import uz.mobile.footzone.utils.BookmarkSavedFilter
import uz.mobile.footzone.utils.CurrentlyOpenFilter
import uz.mobile.footzone.utils.NearStadiumFilter
import uz.mobile.footzone.utils.PreviouslyBookedFilter
import uz.mobile.footzone.utils.StadiumFilter
import uz.mobile.footzone.utils.WellRatedFilter
import uz.mobile.footzone.utils.applyFilter

@OptIn(ExperimentalPermissionsApi::class)
class MainViewModel(
    private val stadiumUseCase: StadiumUseCase,
    private val locationClient: LocationClient,
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

    private lateinit var locationPermissionState: PermissionState

    private val _sideEffect = MutableSharedFlow<MainScreenSideEffects>()
    val sideEffect: SharedFlow<MainScreenSideEffects> = _sideEffect

    private val activeFilters = mutableListOf<StadiumFilter>()

    fun onUiEvent(uiEvent: MainScreenUiEvent) {
        when (uiEvent) {

            is MainScreenUiEvent.OnStadiumSelected -> {
                _state.value = state.value.copy(
                    selectedStadium = uiEvent.selectedStadium,
                )
                hideActionBottomSheet()
            }

            MainScreenUiEvent.PerformSearch -> {
                //search request
            }

            is MainScreenUiEvent.SearchQueryChanged -> {
                _state.value = _state.value.copy(searchQuery = uiEvent.query)
            }

            is MainScreenUiEvent.BottomSheetActionPerformed -> {

                when (uiEvent.bottomSheetAction) {
                    BottomSheetAction.NearStadiums -> {
                        hideActionBottomSheet()

                        toggleFilter(NearStadiumFilter(userLocation = UserLocation()))

                        openListBottomSheet()
                    }

                    BottomSheetAction.SavedStadiums -> {
                        isAuthorisedUser {
                            hideActionBottomSheet()

                            toggleFilter(BookmarkSavedFilter())

                            openListBottomSheet()
                        }
                    }

                    BottomSheetAction.PreviouslyBookedStadiums -> {
                        isAuthorisedUser {
                            hideActionBottomSheet()

                            toggleFilter(PreviouslyBookedFilter())

                            openListBottomSheet()
                        }
                    }

                    BottomSheetAction.MyStadiums -> {
                        isAuthorisedUser {
                            hideActionBottomSheet()

                            openListBottomSheet()
                        }
                    }
                }
            }

            MainScreenUiEvent.CurrentlyOpenFilter -> {
                val isActive = _state.value.currentlyOpenFilterActive

                _state.value = _state.value.copy(
                    currentlyOpenFilterActive = !isActive,
                )

                toggleFilter(CurrentlyOpenFilter())
            }

            MainScreenUiEvent.WellRatedFilter -> {
                val isActive = _state.value.wellRatedFilterActive

                _state.value = _state.value.copy(
                    wellRatedFilterActive = !isActive,
                )

                toggleFilter(WellRatedFilter())
            }

            MainScreenUiEvent.Notification -> {
                viewModelScope.launch {
                    _sideEffect.emit(MainScreenSideEffects.NavigateToNotifications)
                }
            }

            is MainScreenUiEvent.BottomSheetStateChange -> {
                _state.value =
                    _state.value.copy(sheetState = uiEvent.sheetState)
            }

            MainScreenUiEvent.OnBackPressed -> {
                hideListBottomSheet()
                openActionBottomSheet()
                unSelectStadium()
            }

            is MainScreenUiEvent.ListBottomSheetStateChange -> {
                _state.value =
                    _state.value.copy(listSheetState = uiEvent.listSheetState)
            }

            is MainScreenUiEvent.BookStadium -> {
                isAuthorisedUser {

                }
            }

            is MainScreenUiEvent.NavigateToStadium -> {
                val location = Location(uiEvent.stadium.longitude, uiEvent.stadium.latitude)
                viewModelScope.launch {
                    _sideEffect.emit(MainScreenSideEffects.OpenNavigatorChoose(location))
                }
            }

            is MainScreenUiEvent.SaveStadium -> {
                isAuthorisedUser {

                }
            }

            MainScreenUiEvent.RequestLocationPermission -> {
                locationPermissionState.launchPermissionRequest()
            }

            MainScreenUiEvent.DismissDialog -> {
                viewModelScope.launch {
                    _sideEffect.emit(MainScreenSideEffects.Nothing)
                }
            }
        }
    }

    private fun unSelectStadium() {
        _state.value = _state.value.copy(selectedStadium = null)
    }

    private fun toggleFilter(filter: StadiumFilter) {
        if (activeFilters.any { it == filter }) {
            // Filter exists, remove it
            activeFilters.removeIf { it == filter }
        } else {
            // Filter does not exist, add it
            activeFilters.add(filter)
        }
        filterStadiums()
    }

    private fun hideActionBottomSheet() {
        _state.value = _state.value.copy(sheetState = SheetState.Hidden)
    }

    private fun openActionBottomSheet() {
        _state.value = _state.value.copy(sheetState = SheetState.HalfExpanded)
    }

    private fun openListBottomSheet() {
        _state.value = _state.value.copy(listSheetState = SheetState.HalfExpanded)
    }

    private fun hideListBottomSheet() {
        _state.value = _state.value.copy(listSheetState = SheetState.Hidden)
    }

    private fun filterStadiums() {
        val filteredStadiums = applyFilter(_state.value.stadiums, activeFilters)
        _state.value = _state.value.copy(filteredStadiums = filteredStadiums)
    }

    fun clearViewModel() {
        viewModelScope.launch {
            _state.value = MainState(
                stadiums = _state.value.stadiums,
                userType = _state.value.userType,
                searchQuery = _state.value.searchQuery,
                userLocation = _state.value.userLocation
            )
        }
    }

    fun setPermissionState(permissionState: PermissionState) {
        locationPermissionState = permissionState
    }

    fun onLocationPermissionResult(isGranted: Boolean = true) {
        if (isGranted) {
            locationClient.getLocationUpdates(LOCATION_UPDATE_INTERVAl)
                .catch {
                    if (it is LocationClient.LocationException) {
                        viewModelScope.launch {
                            _sideEffect.emit(MainScreenSideEffects.OpenGPSettings)
                        }
                    }
                }
                .onEach { location ->
                    val latitude = location.latitude
                    val longitude = location.longitude
                    val userLocation = Location(longitude, latitude)
                    val userCurrentLocation = _state.value.userLocation
                    _state.value =
                        _state.value.copy(userLocation = userCurrentLocation.copy(point = userLocation))
                }
                .launchIn(viewModelScope)
        }
    }

    private fun isAuthorisedUser(onAuthoriseUser: () -> Unit) {
        if (_state.value.userType != UserType.UNAUTHORIZED)
            onAuthoriseUser()
        else {
            viewModelScope.launch {
                _sideEffect.emit(MainScreenSideEffects.UnAuthorisedUser)
            }
        }
    }

    init {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            stadiumUseCase.getNearbyStadiums()
                .onSuccess { stadiums ->
                    _state.value = MainState(
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