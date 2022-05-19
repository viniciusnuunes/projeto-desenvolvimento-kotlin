package com.example.projeto_desenvolvimento_kotlin

import android.R.attr
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.facebook.CallbackManager


class MMainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mmain)

        callbackManager = CallbackManager.Factory.create();

        val navHostController = supportFragmentManager
            .findFragmentById(R.id.fcvFragment) as NavHostFragment

        navController = navHostController.navController
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}