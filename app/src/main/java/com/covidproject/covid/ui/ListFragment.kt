package com.covidproject.covid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.covidproject.covid.databinding.FragmentListBinding
import com.covidproject.covid.model.data.Vaccine
import com.covidproject.covid.ui.adapter.ListRecyclerAdapter

class ListFragment(var vaccineList : List<Vaccine>) : Fragment() {
    lateinit var binding: FragmentListBinding
    lateinit var listadapter: ListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        listadapter = ListRecyclerAdapter(vaccineList)
        binding.listRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listadapter

        }
        return binding.root
    }

}