package com.example.recipesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.databinding.ItemMethodBinding

class MethodAdapter(private val dataset: List<String>) :
    RecyclerView.Adapter<MethodAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemMethodBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMethodBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stepNumber = position + 1
        val methodText = dataset[position]
        holder.binding.tvMethodStep.text = "$stepNumber. $methodText"
    }

    override fun getItemCount(): Int = dataset.size
}