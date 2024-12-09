package uz.mobile.footzone.presentation.account

import uz.mobile.footzone.common.Constants.EMPTY
import uz.mobile.footzone.common.ErrorState
import uz.mobile.footzone.domain.model.AccountUiModel
import uz.mobile.footzone.domain.model.UserType

data class AccountState (
    val userType: UserType = UserType.USER,
    val account: AccountUiModel,
    val errorState: ErrorState = ErrorState(),
    val isLoading: Boolean = false,
    val appVersion: String = EMPTY,
) {
    constructor() : this(
        userType = UserType.USER,
        account = AccountUiModel(),
        errorState = ErrorState(),
        isLoading = false,
    )
}