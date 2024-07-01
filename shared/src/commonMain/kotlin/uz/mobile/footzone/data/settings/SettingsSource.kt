package uz.mobile.footzone.data.settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

internal enum class SettingsFields {
    ACCESS_TOKEN_DATA
}

class SettingsSourceImpl(private val settings: Settings) : SettingsSource {
    override fun saveToken(token: String) =
        settings.set(SettingsFields.ACCESS_TOKEN_DATA.name, token)

    override fun getToken(): String? =
        settings.getStringOrNull(SettingsFields.ACCESS_TOKEN_DATA.name)
}

interface SettingsSource {
    fun saveToken(token: String)
    fun getToken(): String?
}