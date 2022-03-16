package com.covidproject.covid.vaccine.api

import com.covidproject.covid.BuildConfig
import com.covidproject.covid.vaccine.data.VaccineBody
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(BuildConfig.END_POINT_GET_VACCINE)
    fun getInfo(
        @Query("page")Page:Int,
        @Query("perPage")PerPage:Int,
        @Query("serviceKey")ServiceKey:String = BuildConfig.API_KEY
        ):Observable<VaccineBody>
            //Call<VaccineBody>
}