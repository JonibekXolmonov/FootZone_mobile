package uz.mobile.footzone.android

import android.app.Application
import android.content.Context
import androidx.activity.result.ActivityResultRegistry
import org.koin.dsl.module
import uz.mobile.footzone.android.di.appModule
import uz.mobile.footzone.android.di.coreModule
import uz.mobile.footzone.di.initKoin
import uz.mobile.footzone.platform.initLogger


class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initLogger()
        initKoin(
            module {
                single<Context> { this@MainApp }
            } + appModule + coreModule
        )
    }
}