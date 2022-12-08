package com.example.kotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.model.PhotoModel
import com.example.kotlin.model.PhotoRepository

class PhotoViewModel: ViewModel() {

    val photoModel = MutableLiveData<PhotoModel>()

    fun randomPhoto(){
        val currentPhoto = PhotoRepository.random()
        photoModel.postValue(currentPhoto)
    }
}