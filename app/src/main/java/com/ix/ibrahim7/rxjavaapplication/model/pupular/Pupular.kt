package com.ix.ibrahim7.rxjavaapplication.model.pupular


import com.google.gson.annotations.SerializedName

data class Pupular(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: ArrayList<Result>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)