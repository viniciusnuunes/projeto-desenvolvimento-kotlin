package com.example.projeto_desenvolvimento_kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class FLoginFragment : Fragment(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var loginButton: Button
    private lateinit var callbackManager: CallbackManager
    private lateinit var facebookLoginButton: LoginButton
    private lateinit var rootView: View

    private val EMAIL = "email"
    private val USER_POSTS = "user_posts"
    private val AUTH_TYPE = "rerequest"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callbackManager = CallbackManager.Factory.create();
        auth = FirebaseAuth.getInstance()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_f_login, container, false)

        facebookLoginButton = rootView.findViewById(R.id.btnFacebookLogin)
        facebookLoginButton.setFragment(this)
        facebookLoginButton.setPermissions(Arrays.asList(EMAIL))

        facebookLoginButton.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d("Facebook", "facebook:onSuccess:$loginResult")
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    Log.d("Facebook", "facebook:onCancel")
                    // ...
                }

                override fun onError(error: FacebookException) {
                    Log.d("Facebook", "facebook:onError", error)
                    // ...
                }
            })

        loginButton = rootView.findViewById<Button>(R.id.btnLoginEnter)
        // Ativando a escuta do clique no botão
        loginButton.setOnClickListener(this)

        rootView.findViewById<TextView>(R.id.txtLoginRegister).setOnClickListener {
            findNavController().navigate(R.id.action_FLoginFragment_to_FCreateAccountFragment)
        }

        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)


    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("Facebook", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this.requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Facebook", "signInWithCredential:success")
                    val user = auth.currentUser

                    findNavController().navigate(R.id.action_FLoginFragment_to_FTrendingsFragment)
//                    val mainIntnet = Intent(this, MainActivity::class.java)
//                    startActivity(mainIntnet)
//                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Facebook", "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun signIn(email: String, password: String) {
        if (isValidForm()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this.requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Login", "signInWithEmail:success")
                        val user = auth.currentUser

                        findNavController().navigate(R.id.action_FLoginFragment_to_FTrendingsFragment)
//                        val mainIntnet = Intent(this, MainActivity::class.java)
//                        startActivity(mainIntnet)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Login", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            context,
                            "Authentication failed. " + task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
        } else {
            Toast.makeText(context, "Por favor, preencha os campos", Toast.LENGTH_SHORT).show()
            Log.d("Login", "Campos incorretos")
        }


    }

    private fun isValidForm(): Boolean {
        var valid = true
        val edtEmail = rootView.findViewById<EditText>(R.id.edtLoginEmail)
        val edtPassword = rootView.findViewById<EditText>(R.id.edtLoginPassword)

        val isEmailEmpty: Boolean = edtEmail.text.trim().isEmpty()
        val isPasswordEmpty: Boolean = edtPassword.text.trim().isEmpty()

        if (isEmailEmpty) {
            edtEmail.error = "Obrigatório"
            valid = false
        } else {
            edtEmail.error = null
        }

        if (isPasswordEmpty) {
            edtPassword.error = "Obrigatório"
            valid = false
        } else {
            edtPassword.error = null
        }

        return valid
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnLoginEnter -> {

                val email = rootView.findViewById<EditText>(R.id.edtLoginEmail).text.toString()
                    .trim() { it <= ' ' }
                val password =
                    rootView.findViewById<EditText>(R.id.edtLoginPassword).text.toString()
                        .trim() { it <= ' ' }

                signIn(email, password)
            }
        }
    }


}