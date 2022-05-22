package com.example.projeto_desenvolvimento_kotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.projeto_desenvolvimento_kotlin.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FavoritesFragment : Fragment(), View.OnClickListener {
    private lateinit var fabButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabButton = view.findViewById(R.id.fab_add)

        fabButton.setOnClickListener {
            createNewMovie()
        }
    }

    fun createNewMovie() {
        Toast.makeText(
            context,
            "Cliquei no botão de ADD", Toast.LENGTH_SHORT
        ).show()

//        val nextFrag = Fragment()
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.main_frame_fragments, nextFrag, "MovieFragment")
//            .addToBackStack(null)
//            .commit()
    }


    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.fab_add -> {

            }
        }
    }

}