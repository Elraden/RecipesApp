package com.example.recipesapp

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.databinding.FragmentRecipeBinding
import com.example.recipesapp.entities.Recipe
import com.google.android.material.divider.MaterialDividerItemDecoration
import java.io.IOException

class RecipeFragment : Fragment() {

    companion object {
        const val ARG_RECIPE = "arg_recipe"
    }

    private var recipe: Recipe? = null
    private val binding by lazy { FragmentRecipeBinding.inflate(layoutInflater) }

    private lateinit var ingredientsAdapter: IngredientsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(ARG_RECIPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initRecycler()
    }

    private fun initUi() {
        binding.tvRecipeTitle.text = recipe?.title ?: "Название неизвестно"

        try {
            val inputStream = requireContext().assets.open(recipe?.imageUrl ?: "")
            val drawable: Drawable? = Drawable.createFromStream(inputStream, null)
            binding.imgRecipeHeader.setImageDrawable(drawable)
        } catch (e: IOException) {
            Log.e("IMG_LOAD", "Image not found: ${recipe?.imageUrl}", e)
        }
        binding.tvPortionCount.text = "1"
    }

    private fun initRecycler() {
        ingredientsAdapter = IngredientsAdapter(recipe?.ingredients ?: emptyList())
        val methodAdapter = MethodAdapter(recipe?.method ?: emptyList())

        setupRecyclerView(binding.rvIngredients, ingredientsAdapter)

        setupRecyclerView(binding.rvMethod, methodAdapter)

        binding.seekBarPortions.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvPortionCount.text = progress.toString()
                ingredientsAdapter.updateIngredients(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<*>
    ) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        divider.dividerColor = ContextCompat.getColor(requireContext(), R.color.divider_color)

        val inset = resources.getDimensionPixelSize(R.dimen.margin_inside_card)
        divider.dividerInsetStart = inset
        divider.dividerInsetEnd = inset

        recyclerView.addItemDecoration(divider)
    }
}