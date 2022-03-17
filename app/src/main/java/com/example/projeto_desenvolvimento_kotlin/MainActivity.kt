package com.example.projeto_desenvolvimento_kotlin

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // var binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        ShowMessageAsyncTask().execute()
    }

    inner class ShowMessageAsyncTask() : AsyncTask<String, Int, Boolean>() {



        override fun doInBackground(vararg params: String?): Boolean? {
            startProcess()
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            Toast.makeText(this@MainActivity, "Carregado com sucesso", Toast.LENGTH_LONG).show()
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
        }
    }

    private fun startProcess() {
        Thread.sleep(2000)
    }
}
