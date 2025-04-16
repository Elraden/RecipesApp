package com.example.recipesapp

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.recipesapp.entities.Recipe

class RecipeFragment : Fragment() {

    companion object {
        const val ARG_RECIPE = "arg_recipe"
    }

    private var recipe: Recipe? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(ARG_RECIPE)
        }
        if (view is TextView) {
            val idText = recipe?.id?.toString() ?: "неизвестен"
            val titleText = recipe?.title ?: "неизвестен"

            view.text = "ID: $idText\nРецепт: $titleText"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "Рецепт загружается"
        }
    }

}