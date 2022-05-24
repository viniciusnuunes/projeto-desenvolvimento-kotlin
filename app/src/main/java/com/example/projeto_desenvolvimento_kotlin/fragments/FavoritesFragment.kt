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
import com.example.projeto_desenvolvimento_kotlin.models.FavoriteDBModel
import com.example.projeto_desenvolvimento_kotlin.models.FavoritesModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FavoritesFragment : Fragment(), View.OnClickListener {
    private lateinit var fabButton: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private var favoritesList: MutableList<FavoriteDBModel> = arrayListOf()
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
                        Log.d("Document", document.id)

                        val id = document.id
                        val title = document.data.getValue("title").toString()
                        val episodes = document.data.getValue("episodes").toString()
                        val rating = document.data.getValue("rating").toString()

                        val newFavoriteDbModel = FavoriteDBModel().apply {
                            this.id = id
                            this.title = title
                            this.episodes = episodes
                            this.rating = rating
                        }


                        favoritesList.add(newFavoriteDbModel)
                    }

                    showFavorites(rootView)
                }
            }
    }


    fun showFavorites(view: View) {

        val adapter = MyAdapter(favoritesList)
        val myManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        recyclerView = view.findViewById(R.id.rcFavorites)
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView.layoutManager = myManager;
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val title = favoritesList[position].title
                val episodes = favoritesList[position].episodes
                val rating = favoritesList[position].rating

                val args = Bundle()

//                arguments = Bundle().apply {
//                    putString("FavoriteTitle", title)
//                    putString("FavoriteEpisodes", episodes)
//                    putString("FavoriteRating", rating)
//                    putString("FavoriteId", favoritesList[position].id)
//                }

                args.putString("FavoriteTitle", title)
                args.putString("FavoriteEpisodes", episodes)
                args.putString("FavoriteRating", rating)
                args.putString("FavoriteId", favoritesList[position].id)
                args.putSerializable("FavoriteDB", favoritesList[position])



                findNavController().navigate(R.id.action_favorites_to_add_or_edit, args)
                Log.d("Recycler Click", position.toString())
            }
        })


//        val myManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
//        val myAdaapter: RecyclerView.Adapter<*> = FavoritesFragment.recycleAdapter(favoritesList)
//        recyclerView = view.findViewById(R.id.rcFavorites)
//        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
//        recyclerView.layoutManager = myManager;
//        recyclerView.adapter = myAdaapter;
//        recyclerView.setHasFixedSize(true);
    }

    class MyAdapter(val data: List<FavoriteDBModel>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        private lateinit var mListener: onItemClickListener

        interface onItemClickListener {
            fun onItemClick(position: Int) {

            }
        }

        fun setOnItemClickListener(listener: onItemClickListener) {
            mListener = listener
        }

        fun getCurrentData(): List<FavoriteDBModel> {
            return data
        }

        class MyViewHolder(itemView: View, listener: onItemClickListener) :
            RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.itemFavoriteTitle)
            val episodes: TextView = itemView.findViewById(R.id.itemFavoriteEpisodies)
            val rating: TextView = itemView.findViewById(R.id.itemFavoriteRating)

            init {
                itemView.setOnClickListener {
                    listener.onItemClick(adapterPosition)
                }
            }

            fun bindData(item: FavoriteDBModel) {

                this.title.text = ""
                this.episodes.text = ""
                this.rating.text = ""

                title.text = item.title
                episodes.text = item.episodes
                rating.text = item.rating.toString()
            }

        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bindData(data[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_favorites, parent, false)

            return MyViewHolder(view, mListener)
        }

        override fun getItemCount(): Int {
            return data.size
        }


    }

}