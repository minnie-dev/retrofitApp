package com.covidproject.covid.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.covidproject.covid.databinding.FragmentListBinding
import com.covidproject.covid.model.data.Vaccine
import com.covidproject.covid.ui.adapter.ListRecyclerAdapter
import com.covidproject.covid.viewmodel.RetrofitViewModel

class ListFragment : Fragment() {
    lateinit var binding : FragmentListBinding
    private val retrofitViewModel : RetrofitViewModel by viewModels()
    private var dataList: List<Vaccine>? = null
    lateinit var adapter: ListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater,container,false)

        retrofitViewModel.getVaccine()

        retrofitViewModel.vaccineLiveData.observe(viewLifecycleOwner) {
            if (it.data != dataList) dataList = it.data

            adapter = ListRecyclerAdapter(dataList!!)
            binding.listRecycler.apply {
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                adapter = adapter
            }
            Log.d("TAG", it.toString())
        }

        return binding.root
    }

}