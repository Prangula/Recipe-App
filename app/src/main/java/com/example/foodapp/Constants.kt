package com.example.foodapp

import android.app.Activity
import android.net.Uri
import android.webkit.MimeTypeMap

object Constants {

    const val USERS = "Users"
    const val DETAIL = "detail"
    const val IMAGE = "image" // CLOUDSTORAGE


    const val RECIPES = "Recipes"
    const val RECIPEIMAGE = "recipeImage"
    const val USER_ID = "userId"
    const val DETAIL_RECIPE = "detail_recipe"


    fun getFileExtension(activity: Activity, imageUri: Uri):String{

        // file can be jpg,jpeg,png ... etc
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver .getType(imageUri))!!


    }
}