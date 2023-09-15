package com.example.foodapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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
    var recipe_id:String = ""

):Parcelable
