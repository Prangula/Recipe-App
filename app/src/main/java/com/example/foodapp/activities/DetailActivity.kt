package com.example.foodapp.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.foodapp.Constants
import com.example.foodapp.FirestoreClass
import com.example.foodapp.R
import com.example.foodapp.models.Recipe
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseActivity() {

    private var detailRecipe: String = ""


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        toolbar()

        if (intent.hasExtra(Constants.DETAIL_RECIPE)) {
            detailRecipe = intent.getStringExtra(Constants.DETAIL_RECIPE).toString()
        }


        getDetails()
    }

    private fun toolbar() {
        setSupportActionBar(toolbar_detail)
        toolbar_detail.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        toolbar_detail.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun getDetails() {
        showDialog()
        FirestoreClass().detailRecipe(this@DetailActivity, detailRecipe)
    }

    fun detailSuccess(recipe: Recipe) {
        hideDialog()
        detail_title.text = recipe.title

        Glide
            .with(this)
            .load(recipe.image)
            .into(detail_iv)
        Glide
            .with(this)
            .load(recipe.uploaderImage)
            .into(detail_iv_uploader)

        detail_uploader_name.text = recipe.uploader
        detail_category.text = recipe.category
        detail_time.text = recipe.time
        detail_difficulty.text = recipe.difficulty
        detail_ingredients.text = recipe.ingredient
        detail_howto.text = recipe.howToCook
    }




}
