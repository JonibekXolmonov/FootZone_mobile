package uz.mobile.footzone.utils

import uz.mobile.footzone.domain.model.StadiumUiModel
import uz.mobile.footzone.presentation.main.UserLocation

interface StadiumFilter {
    fun apply(stadiums: List<StadiumUiModel>): List<StadiumUiModel>
}

class NearStadiumFilter(private val userLocation: UserLocation) : StadiumFilter {
    override fun apply(stadiums: List<StadiumUiModel>): List<StadiumUiModel> {
        return stadiums.filter { it.isNear(userLocation) }
    }

    override fun equals(other: Any?): Boolean {
        return other is NearStadiumFilter
//                && other.userLocation == userLocation
    }

    override fun hashCode(): Int {
        return userLocation.hashCode()
    }
}

class BookmarkSavedFilter : StadiumFilter {
    override fun apply(stadiums: List<StadiumUiModel>): List<StadiumUiModel> {
        return stadiums.filter { it.isSaved }
    }

    override fun equals(other: Any?): Boolean {
        return other is BookmarkSavedFilter
    }

    override fun hashCode(): Int {
        return this::class.hashCode()
    }
}

class PreviouslyBookedFilter : StadiumFilter {
    override fun apply(stadiums: List<StadiumUiModel>): List<StadiumUiModel> {
        return stadiums.filter { it.wasPreviouslyBooked }
    }

    override fun equals(other: Any?): Boolean {
        return other is PreviouslyBookedFilter
    }

    override fun hashCode(): Int {
        return this::class.hashCode()
    }
}

class WellRatedFilter(private val minRating: Double = 4.0) : StadiumFilter {
    override fun apply(stadiums: List<StadiumUiModel>): List<StadiumUiModel> {
        return stadiums.filter { it.rating >= minRating }
    }

    override fun equals(other: Any?): Boolean {
        return other is WellRatedFilter
    }

    override fun hashCode(): Int {
        return this::class.hashCode()
    }
}

class CurrentlyOpenFilter : StadiumFilter {
    override fun apply(stadiums: List<StadiumUiModel>): List<StadiumUiModel> {
        return stadiums.filter { it.isOpen }
    }

    override fun equals(other: Any?): Boolean {
        return other is CurrentlyOpenFilter
    }

    override fun hashCode(): Int {
        return this::class.hashCode()
    }
}

fun applyFilter(
    stadiums: List<StadiumUiModel>,
    filters: List<StadiumFilter>
): List<StadiumUiModel> {
    return filters.fold(stadiums) { filteredStadiums, filter ->
        filter.apply(filteredStadiums)
    }
}