package com.example.codingtestex.vaccine.api

import com.example.codingtestex.BuildConfig
import com.example.codingtestex.vaccine.data.VaccineBody
import io.reactivex.Observable
import retrofit2.Call
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