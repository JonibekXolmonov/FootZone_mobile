package uz.mobile.footzone

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()