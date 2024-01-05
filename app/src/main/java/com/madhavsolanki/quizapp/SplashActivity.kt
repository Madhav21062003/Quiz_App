package com.madhavsolanki.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import com.google.firebase.auth.FirebaseAuth
import com.madhavsolanki.quizapp.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        Handler(Looper.getMainLooper()).postDelayed({


            if (auth.currentUser != null){
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        },2500)

    }
}