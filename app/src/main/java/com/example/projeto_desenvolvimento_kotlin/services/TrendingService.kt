package com.example.projeto_desenvolvimento_kotlin.services

import com.example.projeto_desenvolvimento_kotlin.models.TrendingModel
import retrofit2.Call
import retrofit2.http.GET

interface TrendingService {

    @GET("trending/movie/week?api_key=b7e75ba2fb815abd331201e9209af102&language=pt-BR")
    fun list(): Call<List<TrendingModel>>
}