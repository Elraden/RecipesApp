package com.example.recipesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.databinding.ItemIngredientBinding
import com.example.recipesapp.entities.Ingredient
import java.math.RoundingMode

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
        val scaledQuantity = ingredient.quantity
            .toBigDecimal()
            .multiply(quantity.toBigDecimal())
            .setScale(2, RoundingMode.HALF_UP)
            .stripTrailingZeros()

        val formattedQuantity = scaledQuantity.toPlainString()

        holder.binding.tvIngredientName.text = ingredient.description
        holder.binding.tvIngredientAmount.text = "$formattedQuantity ${ingredient.unitOfMeasure}"
    }

    fun updateIngredients(newQuantity: Int) {
        quantity = newQuantity
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataset.size
}