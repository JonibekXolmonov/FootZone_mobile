package uz.mobile.footzone.di

import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.Settings
import org.koin.dsl.module

@OptIn(ExperimentalSettingsImplementation::class)
actual val platformModule = module {
    single<Settings> { KeychainSettings(service = "FootZone") }
}
