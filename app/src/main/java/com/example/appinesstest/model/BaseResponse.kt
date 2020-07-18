package com.example.appinesstest.model

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("photos")
    val photos: Photo,
    @SerializedName("stat")
    val status: String
)