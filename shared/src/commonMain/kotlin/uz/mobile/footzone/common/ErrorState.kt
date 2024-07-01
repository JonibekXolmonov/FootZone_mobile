package uz.mobile.footzone.common

import uz.mobile.footzone.common.Constants.EMPTY

data class ErrorState(
    var hasError: Boolean = false,
    val errorMessage: String = EMPTY,
){
    constructor() :this(
        hasError = false,
        errorMessage = EMPTY
    )
}