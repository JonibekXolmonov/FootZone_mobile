package uz.mobile.footzone.data.remote.network.api.impl

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import uz.mobile.footzone.data.remote.network.api.KtorApi
import uz.mobile.footzone.data.settings.SettingsSource

fun demoToken() = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMiIsImlhdCI6MTcyMDAyNDczMywiaWQiOiIxMiIsInBob25lIjoiOTk4OTMyNDc5Nzc4IiwiZnVsbE5hbWUiOiJKb25pYmVrIFhvbG1vbm92IFFhaHJhbW9uIG8nZydsaSIsImxvY2FsZSI6IkxBIiwiZXhwIjoxNzIwMTExMTMzfQ.aehKHXywPl--qb8S-CrHZnxbnpZzO2xkBXVH7qJiyObAHad-9RcLh25p5OQWkLVU3vbi_2ztxih9GK5fvJoVHg"


class KtorApiImpl(
    private val settingsSource: SettingsSource
) : KtorApi {

    private val prodUrl = "https://footzone.uz/"

    override val client = HttpClient {
        install(HttpTimeout) {
            requestTimeoutMillis = 60_000
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.d(tag = "HTTP Client", message = message)
                }
            }
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    override fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            takeFrom(prodUrl)
            encodedPath = path
            val token = settingsSource.getToken()
            if (!token.isNullOrBlank()) {
                header("Authorization", "Bearer ${demoToken()}")
            }
        }
    }

    override fun HttpRequestBuilder.json() {
        contentType(ContentType.Application.Json)
    }
}
