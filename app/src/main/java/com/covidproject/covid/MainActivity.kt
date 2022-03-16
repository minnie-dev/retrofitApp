package com.covidproject.covid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.covidproject.covid.vaccine.api.RetrofitObject
import com.covidproject.covid.covidtest.Item
import com.covidproject.covid.covidtest.RetrofitClient
import com.covidproject.covid.vaccine.data.Vaccine
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers.io


class MainActivity : AppCompatActivity() {
    private var disposable: Disposable? = null
    private var itemList : List<Item>?=null

    private var dataList : List<Vaccine>?=null
    private var perPage = 284

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCovidTest()
    }

    // 정보불러오기
    private fun getCovidTest() {
        disposable = RetrofitClient.getApiService().getInfo(1,10,"99")
            .observeOn(AndroidSchedulers.mainThread()) // Observable 아이템을 전파할 때 사용할 스레드 지정
            .subscribeOn(io()) // 구독에서 사용할 스레드
            .subscribe({
                Log.d("MainActivity","Success")
                if(it.body.totalCount == perPage) perPage = it.body.totalCount
                if(it.body.items.item != itemList) itemList = it.body.items.item
                Log.d("MainActivity", it.body.items.item.toString())

            },{
                Log.d("MainActivity", it.message.toString())
            })
    }

    // 정보불러오기
    private fun getVaccine() {

        disposable = RetrofitObject.getApiService().getInfo(1, perPage)
            .observeOn(AndroidSchedulers.mainThread()) // Observable 아이템을 전파할 때 사용할 스레드 지정
            .subscribeOn(io()) // 구독에서 사용할 스레드
            .subscribe({
                Log.d("MainActivity","Success")
                if(it.totalCount == perPage) perPage = it.totalCount
                if(it.data != dataList) dataList = it.data

            },{
                Log.d("MainActivity","Fail")
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        //메모리 누수 방지를 위해 사용 후 반드시 자원해제
        disposable?.let {
            disposable!!.dispose() //Observable이 더 이상 데이터를 발행하지 않도록 구독 해지}
        }
    }
}