package com.example.foodapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.foodapp.activities.AddRecipeActivity
import com.example.foodapp.activities.DetailActivity
import com.example.foodapp.activities.LoginActivity
import com.example.foodapp.activities.RegisterActivity
import com.example.foodapp.models.Recipe
import com.example.foodapp.models.Users1
import com.example.foodapp.ui.dashboard.HomeFragment
import com.example.foodapp.ui.home.MyRecipeFragment
import com.example.foodapp.ui.notifications.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirestoreClass {

    private var fireStore = FirebaseFirestore.getInstance()


    fun registerUser(activity: RegisterActivity, user: Users1){

        fireStore.collection(Constants.USERS)
            .document(user.id)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {

                activity.registerSuccess()
            }

    }

    fun logInUser(activity:Activity){

        fireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document->

                val user = document.toObject(Users1::class.java)

                val sharedPreferences =
                    activity.getSharedPreferences(
                        "name",
                        Context.MODE_PRIVATE
                    )

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    "key",
                    "${user!!.name}"
                )
                editor.apply()

                val sharedPreferences1 = activity.getSharedPreferences(
                    "name1",Context.MODE_PRIVATE
                )

                val editor1:SharedPreferences.Editor = sharedPreferences1.edit()
                editor1.putString("key1", "${user.image}")
                editor1.apply()

                when(activity){

                    is LoginActivity ->{
                        activity.logInSuccess(user!!)
                    }


                }

            }
    }

    @SuppressLint("SuspiciousIndentation")
    fun userProfile(fragment:ProfileFragment){

        fireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener {document->

            val user = document.toObject(Users1::class.java)

                fragment.userDetailSuccess(user!!)

            }

    }

    fun registerRecipe(activity: AddRecipeActivity, recipe: Recipe){

        fireStore.collection(Constants.RECIPES)
            .document()
            .set(recipe, SetOptions.merge())
            .addOnSuccessListener {

                activity.recipeRegisterSuccess()

            }


    }


    fun getRecipe(fragment:HomeFragment){


        fireStore.collection(Constants.RECIPES)
            .get()
            .addOnSuccessListener { document->

            val recipeList:ArrayList<Recipe> = ArrayList()

            for(i in document.documents){

                val recipe = i.toObject(Recipe::class.java)!!
                recipe.recipe_id = i.id
                recipeList.add(recipe)


            }
                fragment.rvSuccess(recipeList)


                }

            }




    fun getMyRecipe(fragment:Fragment){

        fireStore
            .collection(Constants.RECIPES)
            .whereEqualTo(Constants.USER_ID,getCurrentUserId())
            .get()
            .addOnSuccessListener {document->

                val myRecipeList:ArrayList<Recipe> = ArrayList()
                for(i in document.documents){

                    val recipe = i.toObject(Recipe::class.java)
                    recipe!!.recipe_id = i.id

                    myRecipeList.add(recipe)
                }

                when(fragment){

                    is MyRecipeFragment ->{

                        fragment.rvSuccess(myRecipeList)

                    }

                }




            }


    }

    fun detailRecipe(activity: DetailActivity, recipe:String){

        fireStore.collection(Constants.RECIPES)
            .document(recipe)
            .get()
            .addOnSuccessListener {document->

                val recipe = document.toObject(Recipe::class.java)

                if(recipe!=null){


                    activity.detailSuccess(recipe)
                }


            }
    }


    fun uploadImageToCloudStorage(activity: Activity, imageUri: Uri, imageType:String){

        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(

            imageType + System.currentTimeMillis() + "."
                    +Constants.getFileExtension(
                activity,
                imageUri)
        )

        sRef.putFile(imageUri)
            .addOnSuccessListener {taskSnapshot->

                Log.e("Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )

                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri->
                        Log.e("Downloadable Image URL",uri.toString())
                        when(activity){

                            is RegisterActivity ->{

                                activity.imageUploadSuccess(uri.toString())

                            }

                            is AddRecipeActivity ->{

                                activity.imageUploadSuccess(uri.toString())
                            }




                        }
                    }

            }

    }



    fun getCurrentUserId():String{

        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserId = ""

        if(currentUser!=null){

            currentUserId = currentUser.uid
        }

        return currentUserId
    }

}