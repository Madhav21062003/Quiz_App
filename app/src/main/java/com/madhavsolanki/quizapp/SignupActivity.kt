package com.madhavsolanki.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.madhavsolanki.quizapp.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var bindind:ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindind = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(bindind.root)

        bindind.btnLogin.setOnClickListener {
            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
        }
    }
}