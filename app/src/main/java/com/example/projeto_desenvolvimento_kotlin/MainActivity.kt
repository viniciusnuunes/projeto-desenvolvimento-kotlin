package com.example.projeto_desenvolvimento_kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.facebook.AccessToken
import com.facebook.login.LoginManager
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
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

        if (currentUser != null || isLoggedIn) {
            Log.d("User", "Usuário Autenticado")

        } else {
            Log.d("User", "Usuário não está Autenticado")

            // Direcionar para a tela de autenticação
            var loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

    // Realizando a visualização do menu do topo
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.top_menu, menu)

        return true
    }

    // Escutando os cliques dentro do menu do topo
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when (item.itemId) {
            R.id.top_menu_logout -> {
                logout()
            }
        }

        return true
    }

    private fun setFragmentTransition(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.main_frame_fragments, fragment)
        fragmentTransition.commit()
    }

    // Escutando os cliques do menu do bottom
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

    private fun logout() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sair")
        builder.setMessage("Deseja desconectar da sua conta?")

        builder.setPositiveButton("Sim") { dialog, which ->
            FirebaseAuth.getInstance().signOut()
            LoginManager.getInstance().logOut()

            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
            Toast.makeText(
                applicationContext,
                "desconectado com sucesso", Toast.LENGTH_SHORT
            ).show()
        }

        builder.setNegativeButton("Não") { dialog, which ->
            Toast.makeText(
                applicationContext,
                "cancelado", Toast.LENGTH_SHORT
            ).show()
        }

        builder.show()
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
