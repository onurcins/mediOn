package com.onurcinstas.medion.service

import com.onurcinstas.medion.model.home.HomeItem
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HomeAPIService {

    private val api = Retrofit.Builder()
        .baseUrl("https://jsonblob.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(HomeAPI::class.java)

    fun getData() : Single<HomeItem> {
        return api.getData()
    }

}