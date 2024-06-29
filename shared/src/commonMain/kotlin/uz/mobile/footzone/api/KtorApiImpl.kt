package uz.mobile.footzone.api

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class KtorApiImpl() : KtorApi {

    val prodUrl = "https://footzone.uz/api/"

    override val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    override fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            takeFrom(prodUrl)
            encodedPath = path
        }
    }

    override fun HttpRequestBuilder.json() {
        contentType(ContentType.Application.Json)
    }
}
