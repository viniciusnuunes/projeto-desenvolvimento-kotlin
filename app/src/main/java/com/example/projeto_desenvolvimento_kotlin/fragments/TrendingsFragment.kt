package com.example.projeto_desenvolvimento_kotlin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_desenvolvimento_kotlin.R
import com.example.projeto_desenvolvimento_kotlin.RetrofitClient
import com.example.projeto_desenvolvimento_kotlin.models.MovieModel
import com.example.projeto_desenvolvimento_kotlin.models.TrendingModel
import com.example.projeto_desenvolvimento_kotlin.services.TrendingService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TrendingsFragment : Fragment() {
    private lateinit var remote: TrendingService
    private lateinit var recyclerView: RecyclerView
    private lateinit var rootView: View
    private var movieList: MutableList<MovieModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        remote = RetrofitClient.createService(TrendingService::class.java)

        // chamada sincrona
        // val response = call.execute()

        getTrendingMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_trendings, container, false)

        showTrendgins(rootView)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        showTrendgins(rootView)
    }

    fun showTrendgins(view: View) {
        val myManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val myAdaapter: RecyclerView.Adapter<*> = TrendingsFragment.recycleAdapter(movieList)
        recyclerView = view.findViewById(R.id.rcTrendings)
        recyclerView.layoutManager = myManager;
        recyclerView.adapter = myAdaapter;
        recyclerView.setHasFixedSize(true);
    }

    fun getTrendingMovies() {
        val call: Call<TrendingModel> = remote.list()

        val response = call.enqueue(object : Callback<TrendingModel> {
            override fun onFailure(call: Call<TrendingModel>, t: Throwable) {
                val s = t.message
                Log.d("Retrofit", t.message.toString())
            }

            override fun onResponse(
                call: Call<TrendingModel>,
                response: Response<TrendingModel>
            ) {
                val s = response.body()
                if (s != null) {
                    for (i in s.results) {
                        movieList.add(i)
                    }

                }
            }
        })

        return response
    }

    class recycleAdapter(val data: List<MovieModel>) :
        RecyclerView.Adapter<recycleAdapter.MyViewHolder>() {

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.itemTitle)
            val originalTitle: TextView = itemView.findViewById(R.id.itemOriginalTitle)
            val releaseDate: TextView = itemView.findViewById(R.id.itemReleaseDate)
            val popularity: TextView = itemView.findViewById(R.id.itemPopularity)

            fun bindData(item: MovieModel) {

                this.title.text = ""
                this.originalTitle.text = ""
                this.releaseDate.text = ""
                this.popularity.text = ""

                title.text = item.title
                originalTitle.text = item.original_title
                releaseDate.text = item.release_date
                popularity.text = item.popularity.toString()

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item__movies, parent, false)

            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bindData(data[position])
        }

        override fun getItemCount(): Int {
            return data.size
        }
    }

    //    class listAdapter(val data: List<MovieModel>) :
//        RecyclerView.Adapter<listAdapter.MyViewHolder>() {
//
//        class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//            val textView = LayoutInflater.from(parent.context)
//                .inflate(R.layout.item__movies, parent, false) as TextView
//            return MyViewHolder(textView)
//
//        }
//
//        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//
////            when (holder.itemId) {
////                R.id.itemTitle -> {
////
////                }
////            }
//            holder.itemId
//            holder.textView.text = data[position].title
//
//        }
//
//        override fun getItemCount() = data.size
//    }

}