package uz.mobile.footzone.api

class ApiService(private val ktorApi: KtorApi) : KtorApi by ktorApi {
    companion object {
        const val STADIUM_BASE_URL = "stadium"
        const val IMAGE_FILE_PART = "attachment"
        const val AUTH_BASE_URL = "auth"
        const val TRANSLATION_BASE_URL = "translate"
    }


}