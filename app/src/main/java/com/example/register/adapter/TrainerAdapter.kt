package com.example.register.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newtrainerapp.retrofit.models.response.TrainerResponse
import com.example.register.databinding.ItemTrainerBinding

class TrainerAdapter : RecyclerView.Adapter<TrainerAdapter.TrainerHolder>() {
    var data = ArrayList<TrainerResponse>()

    inner class TrainerHolder(var binding: ItemTrainerBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: TrainerResponse){
            binding.name.text = data.trainerName
            binding.surname.text = data.trainerSurname
            binding.salary.text = data.trainerSalary.toString()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TrainerHolder(
        ItemTrainerBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: TrainerHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount() = data.size

    fun getData(data:List<TrainerResponse>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}