package com.example.foodapp.models

import android.graphics.Color
import android.os.Parcelable
import com.example.foodapp.R
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class Recipe(

    val image:String = "",
    val title:String = "",
    val category:String = "",
    val time:String = "",
    val difficulty:String = "",
    val ingredient:String = "",
    val howToCook:String = "",
    val uploader:String = "",
    val uploaderImage:String = "",
    val userId:String = "",
    var recipe_id:String = "",
    var date:Date = Date(),



):Parcelable
