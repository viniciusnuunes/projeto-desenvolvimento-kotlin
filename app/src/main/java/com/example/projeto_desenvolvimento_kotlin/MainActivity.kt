package com.example.projeto_desenvolvimento_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var homeFragment: HomeFragment
    private lateinit var favoritesFragment: FavoritesFragment

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        homeFragment = HomeFragment()
        favoritesFragment = FavoritesFragment()

        bottomNavigationView = findViewById(R.id.navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        setFragmentTransition(homeFragment)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            Log.d("User", "Usuário Autenticado")

        } else {
            Log.d("User", "Usuário não está Autenticado")

            // Direcionar para a tela de autenticação
            var loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

    private fun setFragmentTransition(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.main_frame_fragments, fragment)
        fragmentTransition.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> {
                setFragmentTransition(homeFragment)
            }
            R.id.menu_favorites -> {
                setFragmentTransition(favoritesFragment)
            }
        }

        return true
    }

//    inner class ShowMessageAsyncTask() : AsyncTask<String, Int, String>() {
//
//        override fun doInBackground(vararg params: String?): String? {
//            Thread.sleep(5000)
//
//            return "Thread de background executada com sucesso"
//        }
//
//        override fun onPostExecute(result: String?) {
//            super.onPostExecute(result)
//            Toast.makeText(this@MainActivity, result, Toast.LENGTH_LONG).show()
//        }
//
//        override fun onProgressUpdate(vararg values: Int?) {
//            super.onProgressUpdate(*values)
//        }
//    }
}
