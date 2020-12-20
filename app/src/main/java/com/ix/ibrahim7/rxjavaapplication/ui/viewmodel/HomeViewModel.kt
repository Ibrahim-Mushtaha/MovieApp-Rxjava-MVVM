package com.ix.ibrahim7.rxjavaapplication.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ix.ibrahim7.rxjavaapplication.model.pupular.Pupular
import com.ix.ibrahim7.rxjavaapplication.repository.ApiRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import util.Resource

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ApiRepository()
    val dataPupularLiveData = MutableLiveData<Resource<Pupular>>()
    val dataUpcomingLiveData = MutableLiveData<Resource<Pupular>>()
    private val compositeDisposable = CompositeDisposable()
    var data: Pupular? = null

    var page = 1

    fun getPupular() {
        dataPupularLiveData.postValue(Resource.Loading())
     val observable = repository.getPupular(page)
         .subscribeOn(Schedulers.io())
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe(
              {c->
               /*   page++
                  if (data == null){
                      data = c*/
                      dataPupularLiveData.postValue(Resource.Success(c))
                      Log.e("eee dataPupular",data.toString())
/*                  }else{
                      val oldData = c
                      oldData!!.results!!.addAll(c.results!!)
                      dataPupularLiveData.postValue(Resource.Success(oldData))
                      Log.e("eee data2",oldData.toString())
                  }*/

              },
              {x->
                  dataPupularLiveData.postValue(Resource.Error(x.message.toString(),null))
                  Log.e("eee onError", x?.message.toString())
              })

          compositeDisposable.add(observable)
      }


    fun getUpcoming() {
        dataUpcomingLiveData.postValue(Resource.Loading())
        val observable = repository.getUpcoming(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {c->
                 /*   page++
                    if (data == null){
                        data = c*/
                        dataUpcomingLiveData.postValue(Resource.Success(c))
                        Log.e("eee dataUpcoming",data.toString())
                    /*}else{
                        val oldData = c
                        oldData!!.results!!.addAll(c.results!!)
                        dataUpcomingLiveData.postValue(Resource.Success(oldData))
                        Log.e("eee data",oldData.toString())
                    }*/

                },
                {x->
                    dataUpcomingLiveData.postValue(Resource.Error(x.message.toString(),null))
                    Log.e("eee onError", x?.message.toString())
                })

        compositeDisposable.add(observable)
    }


    init {
        getPupular()
        getUpcoming()
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }



}