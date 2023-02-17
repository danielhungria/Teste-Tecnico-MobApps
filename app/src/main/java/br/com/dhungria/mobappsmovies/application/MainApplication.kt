package br.com.dhungria.mobappsmovies.application

import android.app.Application
import com.parse.Parse
import dagger.hilt.android.HiltAndroidApp
import kotlin.io.path.Path

@HiltAndroidApp
class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId("WAjFyX3ecMCrP61nEPGvju87bWM1XaxM20rcWcF5")
                .clientKey("DwJ4sPI2XtOejO87ZRhuvx1J3rtNs85sLsGf41FA")
                .server("https://parseapi.back4app.com/")
                .build()
        )
    }
}