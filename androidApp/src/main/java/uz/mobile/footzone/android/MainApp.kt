package uz.mobile.footzone.android

import android.app.Application
import org.koin.android.ext.koin.androidContext
import uz.mobile.footzone.di.initKoin
import uz.mobile.footzone.platform.initLogger


class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initLogger()
        initKoin {
            androidContext(this@MainApp)
        }
    }
}