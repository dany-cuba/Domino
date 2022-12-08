package com.example.kotlin.model

import com.example.kotlin.R

class PhotoRepository {
    companion object{

        fun random():PhotoModel{
            return photo[photo.indices.random()]
        }
        private val photo: List<PhotoModel> = listOf(
            PhotoModel(R.drawable.photo_1),
            PhotoModel(R.drawable.photo_2),
            PhotoModel(R.drawable.photo_3),
            PhotoModel(R.drawable.photo_4),
            PhotoModel(R.drawable.photo_5),
            PhotoModel(R.drawable.photo_6),
            PhotoModel(R.drawable.photo_7)
        )
    }

}