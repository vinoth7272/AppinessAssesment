package com.example.appinesstest.utils

import com.example.appinesstest.model.FlickrPhoto

object Constant {

    const val BASE_URL = "https://www.flickr.com/"
    const val API_KEY="062a6c0c49e4de1d78497d13a7dbb360"
    const val FORMAT_TYPE= "json"
    const val METHOD_NAME="flickr.photos.search"
    val flickrPhotosList = ArrayList<FlickrPhoto>()
}