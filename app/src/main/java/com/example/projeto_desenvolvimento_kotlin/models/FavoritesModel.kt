package com.example.projeto_desenvolvimento_kotlin.models

import com.google.firebase.firestore.Exclude
import java.io.Serializable

class FavoritesModel(title: String, episodes: String, rating: String) {
    var title: String = title
    var episodes: String = episodes
    var rating: String = rating
}