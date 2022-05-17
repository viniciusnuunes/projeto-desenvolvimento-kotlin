package com.example.projeto_desenvolvimento_kotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projeto_desenvolvimento_kotlin.R


class MovieFragment : Fragment(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onClick(p0: View?) {
//        when(p0?.id) {
//            R.id.fab_add -> {
//                val nextFrag = Fragment()
//                requireActivity().supportFragmentManager.beginTransaction()
//                    .replace(R.id.main_frame_fragments, MovieFragment)
//                    .addToBackStack(null)
//                    .commit()
//            }
//        }
    }

    companion object {

    }


}