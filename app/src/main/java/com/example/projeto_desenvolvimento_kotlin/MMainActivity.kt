package com.example.projeto_desenvolvimento_kotlin

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.projeto_desenvolvimento_kotlin.fragments.FavoritesFragment
import com.example.projeto_desenvolvimento_kotlin.fragments.HomeFragment
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class MMainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth
    val db = FirebaseStorage.getInstance()
    val storageReferente = db.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mmain)

        auth = Firebase.auth

        val navHostController = supportFragmentManager
            .findFragmentById(R.id.fcvFragment) as NavHostFragment

        navController = navHostController.navController
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

        if (currentUser != null || isLoggedIn) {
            Log.d("User", "Usuário Autenticado")
            navController.navigate(R.id.action_FLoginFragment_to_FTrendingsFragment)
        } else {
            Log.d("User", "Usuário não está Autenticado")
        }
    }
}