package uz.mobile.footzone.presentation.stadium_owner

sealed class StadiumOwnerUiEvent {
}

sealed class StadiumOwnerSideEffects {
    data object Nothing : StadiumOwnerSideEffects()
}