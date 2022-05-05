package com.example.projeto_desenvolvimento_kotlin

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.facebook.CallbackManager
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val callbackManager = CallbackManager.Factory.create()
    private val EMAIL = "email"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton: Button = findViewById(R.id.loginButton)

//        loginButton.setReadPermissions(Arrays.asList((EMAIL)))
//        val loginButton: Button = findViewById(R.id.login_button)


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
