package com.covidproject.covid.model

import com.covidproject.covid.BuildConfig
import com.covidproject.covid.model.data.CovidTest
import com.covidproject.covid.model.data.VaccineBody
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface VaccineService {
    @GET(BuildConfig.END_POINT_GET_VACCINE)
    fun getInfo(
        @Query("page")Page:Int,
        @Query("perPage")PerPage:Int,
        @Query("serviceKey")ServiceKey:String = BuildConfig.API_KEY
        ):Single<VaccineBody>
}

interface CovidTestService {
    @GET("B551182/pubReliefHospService/getpubReliefHospList")
    fun getInfo(
        @Query("pageNo")PageNo:Int,
        @Query("numOfRows")NumOfRows:Int,
        @Query("spclAdmTyCd")SpclAdmTyCd:String,
        @Query("serviceKey")ServiceKey:String = BuildConfig.API_KEY
    ): Single<CovidTest>
}