package com.ix.ibrahim7.rxjavaapplication.repository

import com.ix.ibrahim7.rxjavaapplication.network.RetrofitInstance


class ApiRepository {

     fun getPupular(i: Int) =
        RetrofitInstance.api!!.getPupular(pageNumber = i)

    fun getUpcoming(i: Int) =
        RetrofitInstance.api!!.getUpcoming(pageNumber = i)


    fun getMovieDetails(movieID: Int) =
        RetrofitInstance.api!!.getMovieDetails(movie_id = movieID)


}
