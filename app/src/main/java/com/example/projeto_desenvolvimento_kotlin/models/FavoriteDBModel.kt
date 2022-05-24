package com.example.projeto_desenvolvimento_kotlin.models

import java.io.Serializable

class FavoriteDBModel : Serializable {
    lateinit var id: String
    var title: String? = null
    var episodes: String? = null
    var rating: String? = null
}