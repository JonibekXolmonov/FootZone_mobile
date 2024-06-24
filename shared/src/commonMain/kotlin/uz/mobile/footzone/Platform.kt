package uz.mobile.footzone

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform