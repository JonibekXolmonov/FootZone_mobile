package uz.mobile.footzone.domain.model

import uz.mobile.footzone.common.Constants.EMPTY

data class AccountUiModel(
    val id: Int = 0,
    val name: String = EMPTY,
    val image: String? = null,
    val number: String = EMPTY,
){
    val isUrl: Boolean
        get() = !image.isNullOrEmpty() && (image.startsWith("http://") || image.startsWith("https://"))
}