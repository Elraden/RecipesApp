package com.example.recipesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.databinding.ItemIngredientBinding
import com.example.recipesapp.entities.Ingredient

class IngredientsAdapter(private val dataset: List<Ingredient>) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemIngredientBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemIngredientBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient: Ingredient = dataset[position]
        holder.binding.tvIngredientName.text = ingredient.description
        holder.binding.tvIngredientAmount.text = "${ingredient.quantity} ${ingredient.unitOfMeasure}"


    }
    override fun getItemCount(): Int = dataset.size
}