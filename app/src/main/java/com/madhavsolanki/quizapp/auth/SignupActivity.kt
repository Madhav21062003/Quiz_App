package com.madhavsolanki.quizapp.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.madhavsolanki.quizapp.R
import com.madhavsolanki.quizapp.databinding.ActivitySignupBinding
import com.madhavsolanki.quizapp.utils.Utils
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignupActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth // Firebase authentication instance
    private lateinit var bindind:ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindind = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(bindind.root)

        bindind.btnLogin.setOnClickListener {
            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
        }

        mAuth = FirebaseAuth.getInstance()

        bindind.btnSignup.setOnClickListener {
            createUser()
        }


    }

    private fun createUser() {
        Utils.showDialog(this@SignupActivity)
        val name = bindind.etSignupName.text.toString()
        val email = bindind.etSignupEmail.text.toString()
        val password = bindind.etSignupPassword.text.toString()
        val confirmPassword = bindind.etSignupConfirmPassword.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
            lifecycleScope.launch {
                try {
                    val firebaseAuth = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).await()

                    if (firebaseAuth.user != null){
                        // Email Verification
                        Utils.hideDialog()
                        FirebaseAuth.getInstance().currentUser?.sendEmailVerification()?.addOnSuccessListener {
                            val dialog = layoutInflater.inflate(R.layout.accout_dialog, null)
                            val alertDialog = AlertDialog.Builder(this@SignupActivity).setView(dialog).setCancelable(false).create()
                            alertDialog.show()

                            // Handling button Click
                            dialog.findViewById<Button>(R.id.btnOk).setOnClickListener {
                                alertDialog.dismiss()
                                startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                                finish()
                            }
                        }
                    }
                    else{
                        Utils.hideDialog()
                        Utils.showToast(this@SignupActivity, "Signup Failed")
                    }
                }catch (e:Exception){
                    Utils.hideDialog()
                    Utils.showToast(this@SignupActivity, e.message.toString())
                }
            }

        }else{
            Utils.hideDialog()
            Utils.showToast(this@SignupActivity, "Fill all fields")
        }

    }
}