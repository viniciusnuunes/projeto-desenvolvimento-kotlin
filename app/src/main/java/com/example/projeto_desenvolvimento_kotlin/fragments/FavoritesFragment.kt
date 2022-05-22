package com.example.projeto_desenvolvimento_kotlin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_desenvolvimento_kotlin.R
import com.example.projeto_desenvolvimento_kotlin.dao.FavoritesDao
import com.example.projeto_desenvolvimento_kotlin.models.FavoritesModel
import com.example.projeto_desenvolvimento_kotlin.models.MovieModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FavoritesFragment : Fragment(), View.OnClickListener {
    private lateinit var fabButton: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private var favoritesList: MutableList<FavoritesModel> = arrayListOf()
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getFavorites()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_favorites, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabButton = view.findViewById(R.id.fab_add)
        fabButton.setOnClickListener(this)
    }

    fun createNewMovie() {
        Toast.makeText(
            context,
            "Cliquei no botão de ADD", Toast.LENGTH_SHORT
        ).show()

        findNavController().navigate(R.id.action_favorites_to_add_or_edit)
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.fab_add -> {
                createNewMovie()
            }
        }
    }

    fun getFavorites() {
        FavoritesDao().getAll()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result) {
//                        favoritesList.add(document.toObject(FavoritesModel::class.java))
                        val title = document.data.getValue("title").toString()
                        val episodes = document.data.getValue("episodes").toString()
                        val rating = document.data.getValue("rating").toString()
                        val newFavoriteModel = FavoritesModel(title, episodes, rating)
                        favoritesList.add(newFavoriteModel)
                    }

                    showFavorites(rootView)
                }
            }
    }


    fun showFavorites(view: View) {
        val myManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val myAdaapter: RecyclerView.Adapter<*> = FavoritesFragment.recycleAdapter(favoritesList)
        recyclerView = view.findViewById(R.id.rcFavorites)
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView.layoutManager = myManager;
        recyclerView.adapter = myAdaapter;
//        recyclerView.setHasFixedSize(true);
    }

    class recycleAdapter(val data: List<FavoritesModel>) :
        RecyclerView.Adapter<recycleAdapter.MyViewHolder>() {

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.itemFavoriteTitle)
            val episodes: TextView = itemView.findViewById(R.id.itemFavoriteEpisodies)
            val rating: TextView = itemView.findViewById(R.id.itemFavoriteRating)

            fun bindData(item: FavoritesModel) {

                this.title.text = ""
                this.episodes.text = ""
                this.rating.text = ""

                title.text = item.title
                episodes.text = item.episodes
                rating.text = item.rating.toString()
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_favorites, parent, false)

            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bindData(data[position])
        }

        override fun getItemCount(): Int {
            return data.size
        }
    }

}