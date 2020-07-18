package com.example.appinesstest.model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val totalPages: Int,
    @SerializedName("photo")
    val photoList: List<FlickrPhoto>
)