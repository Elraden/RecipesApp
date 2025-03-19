package com.example.recipesapp

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.databinding.ItemCategoryBinding
import com.example.recipesapp.entities.Category
import java.io.IOException
import java.io.InputStream

class CategoryListAdapter(private val dataset: List<Category>) :
    RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick()
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category: Category = dataset[position]
        holder.binding.tvCategoriesTitle.text = category.title
        holder.binding.tvCategoriesDescription.text = category.description

        val context: Context = holder.itemView.context
        holder.binding.imgCategory.contentDescription =
            context.getString(R.string.category_image_description, category.title)

        val drawable = try {
            val inputStream: InputStream? = holder.itemView.context?.assets?.open(category.imageUrl)
            Drawable.createFromStream(inputStream, null)
        } catch (e: IOException) {
            Log.e("INPUT_ERROR", "Image not found: ${category.imageUrl}", e)
            null
        }

        holder.binding.imgCategory.setImageDrawable(drawable)

        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick()
        }
    }

    override fun getItemCount(): Int = dataset.size

}