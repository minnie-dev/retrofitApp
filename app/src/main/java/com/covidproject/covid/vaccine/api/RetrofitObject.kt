package com.covidproject.covid.vaccine.api

import com.covidproject.covid.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_VACCINE)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService():ApiService =
        getRetrofit().create(ApiService::class.java)
}