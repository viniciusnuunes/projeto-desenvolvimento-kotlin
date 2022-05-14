package com.example.projeto_desenvolvimento_kotlin.models

class TrendingModel {
    var page: Int? = null
    lateinit var results: List<MovieModel>
    var total_pages: Int? = null
    var total_results: Int? = null
}