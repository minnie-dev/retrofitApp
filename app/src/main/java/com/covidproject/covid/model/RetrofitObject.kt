package com.covidproject.covid.model

import com.covidproject.covid.BuildConfig
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
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

    fun getApiService(): VaccineService =
        getRetrofit().create(VaccineService::class.java)
}

object RetrofitClient {

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://apis.data.go.kr/B551182/pubReliefHospService/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build()))
            .build()
    }

    fun getApiService(): CovidTestService =
        getRetrofit().create(CovidTestService::class.java)

}