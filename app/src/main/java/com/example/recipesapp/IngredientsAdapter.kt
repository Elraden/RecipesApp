package com.example.recipesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.databinding.ItemIngredientBinding
import com.example.recipesapp.entities.Ingredient

class IngredientsAdapter(private val dataset: List<Ingredient>) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var quantity: Int = 1

    class ViewHolder(val binding: ItemIngredientBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemIngredientBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient: Ingredient = dataset[position]
        val baseQuantity = ingredient.quantity.toDouble()
        val scaledQuantity = baseQuantity * quantity

        val formattedQuantity = if (scaledQuantity % 1 == 0.0) {
            scaledQuantity.toInt().toString()
        } else {
            String.format("%.1f", scaledQuantity)
        }

        holder.binding.tvIngredientName.text = ingredient.description
        holder.binding.tvIngredientAmount.text = "$formattedQuantity ${ingredient.unitOfMeasure}"
    }

    fun updateIngredients(newQuantity: Int) {
        quantity = newQuantity
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataset.size
}