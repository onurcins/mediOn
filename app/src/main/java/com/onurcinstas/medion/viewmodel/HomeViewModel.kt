package com.onurcinstas.medion.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.onurcinstas.medion.R
import com.onurcinstas.medion.model.home.*
import com.onurcinstas.medion.service.HomeAPIService
import com.onurcinstas.medion.util.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class HomeViewModel(application: Application): BaseViewModel(application) {

    private val apiService = HomeAPIService()
    private val disposable = CompositeDisposable()

    val item = MutableLiveData<HomeItem>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun getDataFromAPI() {
        loading.value = true
        error.value = false

        disposable.add(
            apiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<HomeItem>(){
                    override fun onSuccess(t: HomeItem) {
                        Log.d("apiSuccess", t.toString())
                        item.value = t
                    }

                    override fun onError(e: Throwable) {
                        Log.d("apiError", "??")
                        loading.value = false
                        error.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

    fun getArr(context: Context, item: HomeItem): ArrayList<Any>{

        val arr = arrayListOf<Any>()
        arr.add(HomeTitleItem(context.getString(R.string.meditations_title)))
        arr.add(HomeMeditationsRVItem(item.meditations))
        if (item.isBannerEnabled){

            val bannerText = context.getString(R.string.banner_text, App.prefs.getUsername())

            arr.add(HomeBannerItem(bannerText))
        }
        arr.add(HomeTitleItem(context.getString(R.string.stories_title)))
        arr.add(HomeStoriesRVItem(item.stories))

        loading.value = false
        error.value = false

        return arr
    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }

}