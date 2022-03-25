package com.covidproject.covid.model

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object RetrofitClient {

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://apis.data.go.kr/B551182/pubReliefHospService/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build()))
            .build()
    }

    fun getApiService(): CovidTestApi =
        getRetrofit().create(CovidTestApi::class.java)

}