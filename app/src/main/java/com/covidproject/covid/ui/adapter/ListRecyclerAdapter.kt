package com.covidproject.covid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.covidproject.covid.databinding.ItemListBinding
import com.covidproject.covid.model.data.Vaccine

class ListRecyclerAdapter(private val dataList : List<Vaccine>) : RecyclerView.Adapter<ListRecyclerAdapter.ViewHolder>() {
    lateinit var binding : ItemListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : Vaccine){
            binding.nameText.text = data.facilityName
            binding.numberText.text = data.phoneNumber
        }
    }
}

