package uz.mobile.footzone.domain.model

enum class UserType(val value: String) {
    USER("Oddiy foydalanuvchi"),
    STADIUM_OWNER("Maydon egasi"),
    UNAUTHORIZED("Unauthorized")
}