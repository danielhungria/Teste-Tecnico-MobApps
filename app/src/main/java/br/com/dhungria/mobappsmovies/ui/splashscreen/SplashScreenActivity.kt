package br.com.dhungria.mobappsmovies.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import br.com.dhungria.mobappsmovies.MainActivity
import br.com.dhungria.mobappsmovies.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity: AppCompatActivity() {

    private val splashTime: Long = 3000

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, splashTime)
    }

}