package com.madhavsolanki.quizapp.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.madhavsolanki.quizapp.MainActivity
import com.madhavsolanki.quizapp.databinding.ActivityLoginBinding
import com.madhavsolanki.quizapp.databinding.ForgotPasswordDialogBinding
import com.madhavsolanki.quizapp.utils.Utils
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }

        // Setting up the Login button to handle user Authentication
        binding.btnLogin.setOnClickListener {
            // Retrieving email and Password from the input fields
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            // Check if email and password field is Not empty
            if (email.isNotEmpty() && password.isNotEmpty()){
                // Initiating the Login process with the Provided email and Password
                loginUser(email, password)
            }
            else {
                Utils.showToast(this@LoginActivity,"Please enter all Details" )
            }
        }
        // Setting up the text view to handle the "Forgot Password" functionality
        binding.tvForgotPassword.setOnClickListener {
            showForgotPasswordDialog() // Show the dialog for resetting the password
        }
    }



    // Function to display the forgot password dialog
    private fun showForgotPasswordDialog() {
         val dialog = ForgotPasswordDialogBinding.inflate(LayoutInflater.from(this@LoginActivity))
        val alertDialog = AlertDialog.Builder(this@LoginActivity).setView(dialog.root).create()

        alertDialog.setCancelable(false)
        alertDialog.show()

        dialog.tvBackToLogin.setOnClickListener {
            startActivity(Intent(this@LoginActivity, LoginActivity::class.java))
            finish()
        }

        dialog.btnForgotPassword.setOnClickListener {
            val email = dialog.etEmail.text.toString()
            alertDialog.dismiss()
            resetPassword(email)
        }
    }

    private fun resetPassword(email: String) {
            lifecycleScope.launch {
                try {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email).await()
                    Utils.showToast(this@LoginActivity,   "Please Check email and reset your password")
                }
                catch (e:Exception){
                    Utils.showToast(this@LoginActivity,   e.message.toString())
                }
            }
    }

    private fun loginUser(email: String, password: String) {
        Utils.showDialog(this@LoginActivity)

        val firebaseAuth = FirebaseAuth.getInstance()

        lifecycleScope.launch {
            try {
                val authResult = firebaseAuth.signInWithEmailAndPassword(email,password).await()

                // Retrieving the current user's ID and email verification status
                val currentUser = authResult.user?.uid
                val verifyEmail = firebaseAuth.currentUser?.isEmailVerified

                // Checking if email is verified
                if (verifyEmail == true){
                    Utils.hideDialog()

                    // If the user is verified , retrieve user data and navigate  to the appropriate activity
                    if (currentUser != null){
                        Utils.hideDialog()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }else {
                        Utils.hideDialog()
                        Utils.showToast(this@LoginActivity, "User Not Found Signup First")

                    }
                }else{
                    Utils.hideDialog()
                    Utils.showToast(this@LoginActivity, "Email Not Verified")
                    binding.etEmail.text.clear()
                    binding.etPassword.text.clear()
                }
            }
            catch (e:Exception){
                Utils.hideDialog()
                Utils.showToast(this@LoginActivity, e.message!!) // Displaying an error message if an exception occurs
                binding.etEmail.text.clear()
                binding.etPassword.text.clear()
            }
        }
    }
}