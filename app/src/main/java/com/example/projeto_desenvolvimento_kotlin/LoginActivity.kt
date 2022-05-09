package com.example.projeto_desenvolvimento_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        loginButton = findViewById(R.id.btnLoginEnter)
        loginButton.setOnClickListener(this)

        val registerIntent = Intent(this,  RegisterActivity::class.java)

        findViewById<TextView>(R.id.txtLoginRegister).setOnClickListener {
            startActivity(registerIntent)
        }
    }

    override fun onResume() {
        super.onResume()

//        val userId = intent.getStringExtra("user_id")
//        val emailId = intent.getStringExtra("email_id")
//
//        val txtUserId = findViewById<TextView>(R.id.txtUserId)
//        val txtEmailId = findViewById<TextView>(R.id.txtEmailId)
//
//        txtUserId.text = "User ID: $userId"
//        txtEmailId.text = "User Email: $emailId"
//
//        txtUserId.isVisible = true
//        txtEmailId.isVisible = true
//
//        Log.d("Extra", userId.toString())
//        Log.d("Extra", emailId.toString())
//
//
//        val edtEmail: EditText = findViewById(R.id.edtLoginEmail)
//        edtEmail.setText(emailId)

    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.btnLoginEnter -> {
                val email = findViewById<EditText>(R.id.edtLoginEmail).text.toString().trim() {it <= ' '}
                val password = findViewById<EditText>(R.id.edtLoginPassword).text.toString().trim() {it <= ' '}

                signIn(email, password)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        if(isValidForm()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Login", "signInWithEmail:success")
                        val user = auth.currentUser

                        val mainIntnet = Intent(this, MainActivity::class.java)
                        startActivity(mainIntnet)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Login", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed. " + task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT).show()

                    }
                }
        } else {
            Toast.makeText(this, "Por favor, preencha os campos", Toast.LENGTH_SHORT).show()
            Log.d("Login", "Campos incorretos")
        }


    }

    private fun isValidForm(): Boolean {
        var valid = true
        val email = findViewById<EditText>(R.id.edtLoginEmail)
        val password = findViewById<EditText>(R.id.edtLoginPassword)

        val isEmailEmpty: Boolean = email.text.trim().isEmpty()
        val isPasswordEmpty: Boolean = password.text.trim().isEmpty()

        if (isEmailEmpty) {
            email.error = "Obrigatório"
            valid = false
        } else {
            email.error = null
        }

        if (isPasswordEmpty) {
            password.error = "Obrigatório"
            valid = false
        } else {
            password.error = null
        }

        return valid
    }

//    override fun onStart() {
//        super.onStart()
//        val currentUser = auth.currentUser
//
//        Log.d("CurrentUser", currentUser.toString())
//    }

//    private fun reload() {
//        auth.currentUser!!.reload().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                updateUI(auth.currentUser)
//                Toast.makeText(this, "Reload successful!", Toast.LENGTH_SHORT).show()
//            } else {
//                Log.e("ReloadUser", "reload", task.exception)
//                Toast.makeText(this, "Failed to reload user.", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//
//    private fun updateUI(user: FirebaseUser?) {
//
//        if (user != null) {
//            binding.status.text = getString(R.string.emailpassword_status_fmt,
//                user.email, user.isEmailVerified)
//            binding.detail.text = getString(R.string.firebase_status_fmt, user.uid)
//
//            binding.emailPasswordButtons.visibility = View.GONE
//            binding.emailPasswordFields.visibility = View.GONE
//            binding.signedInButtons.visibility = View.VISIBLE
//
//            if (user.isEmailVerified) {
//                binding.verifyEmailButton.visibility = View.GONE
//            } else {
//                binding.verifyEmailButton.visibility = View.VISIBLE
//            }
//        } else {
//            binding.status.setText(R.string.signed_out)
//            binding.detail.text = null
//
//            binding.emailPasswordButtons.visibility = View.VISIBLE
//            binding.emailPasswordFields.visibility = View.VISIBLE
//            binding.signedInButtons.visibility = View.GONE
//        }
//    }



}