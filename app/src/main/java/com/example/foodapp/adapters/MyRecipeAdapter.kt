package com.example.foodapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.Constants
import com.example.foodapp.models.Recipe
import com.example.foodapp.activities.DetailActivity
import com.example.foodapp.databinding.MyrecipeitemBinding
import com.example.foodapp.ui.home.MyRecipeFragment

class MyRecipeAdapter(private val items:ArrayList<Recipe>,
                      private val fragment:MyRecipeFragment)
    :RecyclerView.Adapter<MyRecipeAdapter.ViewHolder>(){


        inner class ViewHolder(binding:MyrecipeitemBinding)
            :RecyclerView.ViewHolder(binding.root){

            val image = binding.itemMyRecipeIv1
            val text = binding.itemMyRecipeText

            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MyrecipeitemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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

        holder.itemView.setOnLongClickListener {

            fragment.deleteNews(item.recipe_id)
            true
        }



    }
}

