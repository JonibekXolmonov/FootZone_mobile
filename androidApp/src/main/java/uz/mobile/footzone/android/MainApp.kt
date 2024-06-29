package uz.mobile.footzone.android

import android.app.Application
import org.koin.android.ext.koin.androidContext
import uz.mobile.footzone.di.initKoin


class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@MainApp)
        }
    }
}