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

    companion object {
        const val ARG_RECIPE = "arg_recipe"
    }

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    private val binding by lazy { FragmentRecipesListBinding.inflate(layoutInflater) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBundleData()
        initUI()
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

    private fun initUI() {
        val recipes = STUB.getRecipesByCategoryId(categoryId ?: 0)
        val adapter = RecipeListAdapter(recipes)

        binding.tvCategoryTitle.text = categoryName
        binding.rvRecipes.adapter = adapter

        try {
            val inputStream = requireContext().assets.open(categoryImageUrl ?: "")
            val drawable = Drawable.createFromStream(inputStream, null)
            binding.imgCategoryHeader.setImageDrawable(drawable)
        } catch (e: IOException) {
            Log.e("IMG_LOAD", "Image not found: $categoryImageUrl", e)
        }

        adapter.setOnItemClickListener(object : RecipeListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
    }

    private fun openRecipeByRecipeId(recipeId: Int) {

        val recipe = STUB.getRecipeByID(recipeId)
        if (recipe != null) {
            val bundle = Bundle().apply {
                putParcelable(ARG_RECIPE, recipe)
            }

            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<RecipeFragment>(R.id.fmContainer, args = bundle)
                addToBackStack(null)
            }
        }
    }
}