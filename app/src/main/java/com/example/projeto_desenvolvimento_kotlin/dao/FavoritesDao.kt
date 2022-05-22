package com.example.projeto_desenvolvimento_kotlin.dao

import com.example.projeto_desenvolvimento_kotlin.models.FavoritesModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage

class FavoritesDao {
    val db = FirebaseFirestore.getInstance()
    val collection = "favorites"

    fun add(FavoriteModel: FavoritesModel): Task<DocumentReference> {
        val task = db.collection(collection).add(FavoriteModel)

        return task
    }

    fun getAll(): Task<QuerySnapshot> {
        val task = db.collection(collection).get()

        return task
    }
}