package com.ix.ibrahim7.rxjavaapplication.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ix.ibrahim7.rxjavaapplication.model.details.MovieDetails
import com.ix.ibrahim7.rxjavaapplication.model.pupular.Pupular
import com.ix.ibrahim7.rxjavaapplication.repository.ApiRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import util.Resource

class DetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ApiRepository()
    val dataDetailsLiveData = MutableLiveData<Resource<MovieDetails>>()
    private val compositeDisposable = CompositeDisposable()

    fun getMovieDetails(movieID: Int) {
        dataDetailsLiveData.postValue(Resource.Loading())
        val observable = repository.getMovieDetails(movieID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { c ->
                    dataDetailsLiveData.postValue(Resource.Success(c))
                },
                { x ->
                    dataDetailsLiveData.postValue(Resource.Error(x.message.toString(), null))
                    Log.e("eee onError", x?.message.toString())
                })

        compositeDisposable.add(observable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


}