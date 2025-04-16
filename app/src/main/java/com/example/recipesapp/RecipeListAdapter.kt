package com.example.recipesapp

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.databinding.ItemRecipeBinding
import com.example.recipesapp.entities.Recipe
import java.io.IOException
import java.io.InputStream

class RecipeListAdapter(private val dataset: List<Recipe>) :
    RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(recipeId: Int)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = dataset[position]
        val context: Context = holder.itemView.context

        holder.binding.tvRecipeTitle.text = recipe.title
        holder.binding.imgRecipe.contentDescription =
            context.getString(R.string.recipe_image_description, recipe.title)

        val drawable = try {
            val inputStream: InputStream? = holder.itemView.context?.assets?.open(recipe.imageUrl)
            Drawable.createFromStream(inputStream, null)
        } catch (e: IOException) {
            Log.e("INPUT_ERROR", "Image not found: ${recipe.imageUrl}", e)
            null
        }

        holder.binding.imgRecipe.setImageDrawable(drawable)

        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(recipe.id)
        }
    }

    override fun getItemCount(): Int = dataset.size
}