package com.example.appinesstest.network

import com.example.appinesstest.model.BaseResponse
import com.example.appinesstest.utils.Constant.API_KEY
import com.example.appinesstest.utils.Constant.FORMAT_TYPE
import com.example.appinesstest.utils.Constant.METHOD_NAME
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("services/rest/")
    suspend fun getFlickrImages(
        @Query("method") methodName: String = METHOD_NAME,
        @Query("nojsoncallback") callback: String = "1",
        @Query("text") searchText: String,
        @Query("format") formatType: String = FORMAT_TYPE,
        @Query("per_page") perPage : Int = 6,
        @Query("page") page :Int,
        @Query("api_key") apiKey:String = API_KEY
    ) : BaseResponse
}