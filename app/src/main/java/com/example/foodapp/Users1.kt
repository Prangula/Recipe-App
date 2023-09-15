package com.example.foodapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users1(

    val id:String ="",
    val name:String ="",
    val surname:String = "",
    val email:String = "",
    val mobile:String = "",
    val image:String = "",
    val gender:String = "",
):Parcelable
