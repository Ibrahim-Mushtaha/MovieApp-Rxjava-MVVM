package com.ix.ibrahim7.rxjavaapplication.network

import com.ix.ibrahim7.rxjavaapplication.model.pupular.Pupular
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*
import util.Constant.API_KEY

interface Api {



    @GET("3/movie/popular")
    fun getPupular(
            @Query("page")
            pageNumber: Int = 1,
            @Query("language")
            language: String = "en-US",
            @Query("api_key")
            apiKey: String = API_KEY
    ): Single<Pupular>

    @GET("3/movie/upcoming")
    fun getUpcoming(
            @Query("page")
            pageNumber: Int = 1,
            @Query("language")
            language: String = "en-US",
            @Query("api_key")
            apiKey: String = API_KEY
    ): Single<Pupular>

}
