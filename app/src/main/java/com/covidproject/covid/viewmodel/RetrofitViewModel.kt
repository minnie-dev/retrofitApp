package com.covidproject.covid.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import com.covidproject.covid.model.*
import com.covidproject.covid.model.data.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RetrofitViewModel : ViewModel() {

    private var TAG = "ViewModel"
    var covidTestLiveData = MutableLiveData<CovidTest>()
    var vaccineLiveData = MutableLiveData<VaccineBody>()

    @SuppressLint("CheckResult")
    fun getCovidTest() {
        RetrofitClient.getApiService().getInfo(1, 100, "99")
            .observeOn(AndroidSchedulers.mainThread()) // Observable 아이템을 전파할 때 사용할 스레드 지정
            .subscribeOn(Schedulers.io()) // 구독에서 사용할 스레드
            .subscribe({
                Log.d(TAG, "coivdTest success")
                covidTestLiveData.value = it
            }, {
                Log.d(TAG, it.toString())
            })
    }

    @SuppressLint("CheckResult")
    fun getVaccine() {
        RetrofitObject.getApiService().getInfo(1, 20)
            .observeOn(AndroidSchedulers.mainThread()) // Observable 아이템을 전파할 때 사용할 스레드 지정
            .subscribeOn(Schedulers.io()) // 구독에서 사용할 스레드
            .subscribe({
                Log.d(TAG, "vaccine success")
                vaccineLiveData.value = it
            }, {
                Log.d(TAG, it.toString())
            })
    }
}