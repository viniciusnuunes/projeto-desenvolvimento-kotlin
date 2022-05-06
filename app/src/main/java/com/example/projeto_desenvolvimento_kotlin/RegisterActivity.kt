package com.example.projeto_desenvolvimento_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var currentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val edtEmail = findViewById<EditText>(R.id.edtRegisterEmail)
        val edtPassword = findViewById<EditText>(R.id.edtRegisterPassword)

        auth = Firebase.auth

        findViewById<Button>(R.id.btnRegister).setOnClickListener{
            if (isValidForm()) {
                Log.d("Validate", "Formulário Válido")
                val email = edtEmail.text.toString().trim() {it <= ' '}
                val password = edtPassword.text.toString().trim() {it <= ' '}


                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            currentUser = task.result!!.user!!
                            Log.d("Registering", "createUserWithEmail:success")
                            Log.d("Registered", currentUser.uid)
                            Log.d("Registered", email)

                            Toast.makeText(this, "Cadastro feito com sucesso", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, LoginActivity::class.java)

                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("user_id", currentUser.uid)
                            intent.putExtra("email_id", email)
                            intent.putExtra("current_user", currentUser)
                            startActivity(intent)
                            finish()
                        } else {
                            Log.w("Registering", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed." + task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT).show()
                        }
                    }


            } else {
                Toast.makeText(this, "Por favor, preencha os campos", Toast.LENGTH_SHORT).show()
                Log.d("Validate", "Formulário Inválido")
            }

        }

    }

    private fun register(auth: FirebaseAuth, email: String, password: String): Boolean {
        var registered = false

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    currentUser = task.result!!.user!!
                    Log.d("Registering", "createUserWithEmail:success")
                    Toast.makeText(this, "Cadastro feito com sucesso", Toast.LENGTH_SHORT).show()
                    registered = true
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Registering", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed." + task.exception!!.message.toString(),
                        Toast.LENGTH_SHORT).show()
                    registered = false
                }
            }
        return registered
    }

    private fun isValidForm(): Boolean {
        var valid = true
        val email = findViewById<EditText>(R.id.edtRegisterEmail)
        val password = findViewById<EditText>(R.id.edtRegisterPassword)

        val isEmailEmpty: Boolean = email.text.trim().isEmpty()
        val isPasswordEmpty: Boolean = password.text.trim().isEmpty()

        if (isEmailEmpty) {
            email.error = "Obrigatório"
            valid = false
        } else {
            email.error = null
        }

        if (isEmailEmpty) {
            password.error = "Obrigatório"
            valid = false
        } else {
            password.error = null
        }

        return valid
    }
}