package com.example.appinesstest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appinesstest.model.FlickrPhoto
import com.example.appinesstest.model.Resource
import com.example.appinesstest.network.ApiService
import kotlinx.coroutines.launch
import java.lang.Exception

class ImageViewModel(private val apiService: ApiService) : ViewModel() {


    var resource = MutableLiveData<Resource<List<FlickrPhoto>>>()
    private var pageNumber: Int = 1
    var isFirstPage = true


    fun fetchFlickr(searchString: String) {
        viewModelScope.launch {
            val response = apiService.getFlickrImages(searchText = searchString, page = pageNumber)
            try {
                if (response.status == "ok") {
                    resource.postValue(Resource.success(response.photos.photoList))
                    pageNumber = response.photos.page + 1
                } else
                    resource.postValue(Resource.error(response.status, null))
            } catch (e: Exception) {
                resource.postValue(Resource.error(e.toString(), null))
            }
        }
    }



}