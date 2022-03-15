package com.example.codingtestex.covidtest

import com.example.codingtestex.BuildConfig
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CovidTestApi {
    @GET("getpubReliefHospList")
    fun getInfo(
        @Query("pageNo")PageNo:Int,
        @Query("numOfRows")NumOfRows:Int,
        @Query("spclAdmTyCd")SpclAdmTyCd:String,
        @Query("serviceKey")ServiceKey:String = BuildConfig.API_KEY
    ): Observable<CovidTest>
}