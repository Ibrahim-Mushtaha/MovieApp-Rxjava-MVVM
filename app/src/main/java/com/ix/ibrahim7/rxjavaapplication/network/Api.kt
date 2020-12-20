package com.ix.ibrahim7.rxjavaapplication.network

import com.ix.ibrahim7.rxjavaapplication.model.details.MovieDetails
import com.ix.ibrahim7.rxjavaapplication.model.pupular.Pupular
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*
import util.Constant.API_KEY

interface Api {



    @GET("movie/popular")
    fun getPupular(
            @Query("page")
            pageNumber: Int = 1,
            @Query("language")
            language: String = "en-US",
            @Query("api_key")
            apiKey: String = API_KEY
    ): Single<Pupular>

    @GET("movie/upcoming")
    fun getUpcoming(
            @Query("page")
            pageNumber: Int = 1,
            @Query("language")
            language: String = "en-US",
            @Query("api_key")
            apiKey: String = API_KEY
    ): Single<Pupular>


    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id")
        movie_id: Int,
        @Query("language")
        language: String = "en-US",
        @Query("api_key")
        apiKey: String = API_KEY
    ): Single<MovieDetails>

}
