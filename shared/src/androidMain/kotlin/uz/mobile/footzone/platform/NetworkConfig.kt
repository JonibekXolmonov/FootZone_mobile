package uz.mobile.footzone.platform

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp) {
    config(this)

    engine {
        config {
            retryOnConnectionFailure(true)
            connectTimeout(5, TimeUnit.SECONDS)
        }
    }
}

actual fun initLogger() {
    Napier.base(DebugAntilog())
}

actual fun Exception.cast(): Exception {
    return when (this) {
        is UnknownHostException -> Exception("Serverga ulanishda xatolik")
        else -> this
    }
}