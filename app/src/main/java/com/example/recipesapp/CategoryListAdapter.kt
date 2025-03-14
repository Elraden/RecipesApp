package com.example.recipesapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.entities.Category
import java.io.InputStream

class CategoryListAdapter(private val dataset: List<Category>) :
    RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imgCategory)
        val titleTextView: TextView = view.findViewById(R.id.tvCategoriesTitle)
        val descriptionTextView: TextView = view.findViewById(R.id.tvCategoriesDescription)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category: Category = dataset[position]
        holder.titleTextView.text = category.title
        holder.descriptionTextView.text = category.description

        val drawable = try {
            val inputStream: InputStream? = holder.itemView.context?.assets?.open(category.imageUrl)
            Drawable.createFromStream(inputStream, null)
        } catch (e: Exception) {
            Log.d("INPUT_ERROR", "Image not found: ${category.imageUrl}")
            null
        }
        holder.imageView.setImageDrawable(drawable)

    }

    override fun getItemCount(): Int = dataset.size


}