package com.example.projeto_desenvolvimento_kotlin

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitClient {

    companion object {
        private lateinit var retrofit: Retrofit
        private val BASE_URL = "https://api.themoviedb.org/3/"

        private fun getRetrofitInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            if (!::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit
        }

        fun <S> createService(serviceClass: Class<S>): S {
            return getRetrofitInstance().create(serviceClass)
        }
    }
}