package com.amaze.testproject.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.amaze.testproject.R
import com.amaze.testproject.other.Utilities
import java.util.*

class SplashActivity :AppCompatActivity(){
    private val SPLASH_TIME_OUT:Long = 3000

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        Utilities.setStatusBarColor(this)

        val handler = Handler()
        handler.postDelayed({
            Intent_ToMain()
        }, SPLASH_TIME_OUT)
    }

    fun Intent_ToMain() {
        val intent: Intent
        intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        this@SplashActivity.finish()
     }


}