package com.kalashnyk.denys.splash_operation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.myapplication.R
import com.kalashnyk.denys.main_operation.MainActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        val myIntent = Intent(this, MainActivity::class.java)
        super.onResume()
        Handler().postDelayed({
            startActivity(myIntent)
            finish()
        }, 3000)
    }
}