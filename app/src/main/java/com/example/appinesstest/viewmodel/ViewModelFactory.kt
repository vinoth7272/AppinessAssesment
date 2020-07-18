package com.example.appinesstest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appinesstest.network.ApiService

class ViewModelFactory(val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageViewModel::class.java))
            return ImageViewModel(apiService) as T
        else
            throw IllegalArgumentException("Unknown viewmodel class")
    }
}