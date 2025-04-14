package com.example.recipesapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipesapp.databinding.FragmentRecipesListBinding
import java.io.IOException

class RecipesListFragment : Fragment() {

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    private val binding by lazy { FragmentRecipesListBinding.inflate(layoutInflater) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBundleData()
        initHeader()
        initRecycler()
    }

    private fun initBundleData() {
        arguments?.let { bundle ->
            categoryId = bundle.getInt(CategoriesListFragment.ARG_CATEGORY_ID)
            categoryName = bundle.getString(CategoriesListFragment.ARG_CATEGORY_NAME)
            categoryImageUrl = bundle.getString(CategoriesListFragment.ARG_CATEGORY_IMAGE_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun initHeader() {
        binding.tvCategoryTitle.text = categoryName

        try {
            val inputStream = requireContext().assets.open(categoryImageUrl ?: "")
            val drawable = Drawable.createFromStream(inputStream, null)
            binding.imgCategoryHeader.setImageDrawable(drawable)
        } catch (e: IOException) {
            Log.e("IMG_LOAD", "Image not found: $categoryImageUrl", e)
        }
    }

    private fun initRecycler() {
        val recipes = STUB.getRecipesByCategoryId(categoryId ?: 0)
        val adapter = RecipeListAdapter(recipes)

        binding.rvRecipes.adapter = adapter

        adapter.setOnItemClickListener(object : RecipeListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.fmContainer)
            addToBackStack(null)
        }
    }
}