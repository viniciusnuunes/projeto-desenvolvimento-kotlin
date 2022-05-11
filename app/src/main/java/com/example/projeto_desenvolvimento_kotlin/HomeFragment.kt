package com.example.projeto_desenvolvimento_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call as Call

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val remote = RetrofitClient.createService(TrendingService::class.java)
        val call: Call<List<TrendingModel>> = remote.list()

        // chamada sincrona
        // val response = call.execute()

        val response = call.enqueue(object : Callback<List<TrendingModel>> {
            override fun onFailure(call: Call<List<TrendingModel>>, t: Throwable) {
                val s = t.message
            }

            override fun onResponse(
                call: Call<List<TrendingModel>>,
                response: Response<List<TrendingModel>>
            ) {
                val s = response.body()
            }
        })



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}