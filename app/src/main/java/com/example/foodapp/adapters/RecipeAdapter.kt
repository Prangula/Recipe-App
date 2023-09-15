package com.example.foodapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.Constants
import com.example.foodapp.models.Recipe
import com.example.foodapp.activities.DetailActivity
import com.example.foodapp.databinding.RecipeitemBinding
import com.example.foodapp.ui.dashboard.HomeFragment

class RecipeAdapter(private val items:ArrayList<Recipe>,
                    private val fragment:HomeFragment)
    :RecyclerView.Adapter<RecipeAdapter.ViewHolder>(){


        inner class ViewHolder(binding:RecipeitemBinding)
            :RecyclerView.ViewHolder(binding.root){

            val image = binding.itemRecipeIv1
            val text = binding.itemRecipeText





            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecipeitemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        Glide
            .with(holder.itemView.context)
            .load(item.image)
            .into(holder.image)

        holder.text.text = item.title

        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(Constants.DETAIL_RECIPE,item.recipe_id)
            holder.itemView.context.startActivity(intent)

        }

    }
}

