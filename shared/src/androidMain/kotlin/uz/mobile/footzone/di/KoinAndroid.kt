package uz.mobile.footzone.di

import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module
import uz.mobile.footzone.settings.EncryptedSettingsFactory

actual val platformModule: Module = module {
    single {
        val factory: Settings.Factory = EncryptedSettingsFactory(get())
        val create = factory.create()
        create
    }
}