package com.onurcinstas.medion.service

import com.onurcinstas.medion.model.home.HomeItem

import io.reactivex.Single
import retrofit2.http.GET

interface HomeAPI {

    @GET("api/jsonBlob/a07152f5-775c-11eb-a533-c90b9d55001f")
    fun getData(): Single<HomeItem>
}