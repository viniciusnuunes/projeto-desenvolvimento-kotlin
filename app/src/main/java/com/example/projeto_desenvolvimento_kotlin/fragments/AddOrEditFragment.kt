package com.example.projeto_desenvolvimento_kotlin.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_desenvolvimento_kotlin.R
import com.example.projeto_desenvolvimento_kotlin.dao.FavoritesDao
import com.example.projeto_desenvolvimento_kotlin.models.FavoritesModel
import com.example.projeto_desenvolvimento_kotlin.models.MovieModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.storage.FirebaseStorage


class AddOrEditFragment : Fragment(), View.OnClickListener {
    private lateinit var rootView: View
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var saveButton: Button

    val db = FirebaseStorage.getInstance()
    val storageReferente = db.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_add_or_edit, container, false)

//        bottomNavigationView = rootView.findViewById(R.id.navigation_view)
        saveButton = rootView.findViewById(R.id.btnSave)

        saveButton.setOnClickListener(this)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btnSave -> {
                saveNewFavorite()
            }
        }
    }

    private fun saveNewFavorite() {
        var title = rootView.findViewById<EditText>(R.id.edtMovieTitle).text.toString().trim() {it <= ' '}
        var episodes = rootView.findViewById<EditText>(R.id.edtWatchedEpisodes).text.toString().trim() {it <= ' '}
        var rating = rootView.findViewById<EditText>(R.id.edtMovieRating).text.toString().trim() {it <= ' '}

        var newFavorite = FavoritesModel(title, episodes, rating)

        FavoritesDao().add(newFavorite).addOnFailureListener {
            Toast.makeText(
                context,
                it.message.toString(), Toast.LENGTH_SHORT
            ).show()
        }
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Salvei corretamente o filme", Toast.LENGTH_SHORT
                ).show()

                findNavController().navigate(R.id.action_add_to_favorites)
            }
    }


}