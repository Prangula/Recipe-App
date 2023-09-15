package com.example.foodapp

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_add_recipe.*
import kotlinx.android.synthetic.main.activity_register.*

class AddRecipeActivity : BaseActivity() {

    private var imageUri:Uri? = null
    private var imageUrl:String = ""
    private var category:String = ""
    private var difficulty:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        toolbar()

        recipe_iv.setOnClickListener {

            gallery()
        }

        recipe_button.setOnClickListener {

            addRecipe()
        }


    }

    private fun toolbar(){

     setSupportActionBar(toolbar_recipe)
     toolbar_recipe.setNavigationIcon(R.drawable.baseline_arrow_back_24)

     toolbar_recipe.setNavigationOnClickListener {
         onBackPressed()
     }

    }

    private fun gallery(){

        Dexter.withActivity(this)
            .withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if(report!!.areAllPermissionsGranted()){

                        val galleryIntent = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(galleryIntent, GALLERY)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showRationaleDialogForPermissions()
                }


            }).onSameThread().check()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK&&requestCode== GALLERY&&data!!.data!=null){

            imageUri = data!!.data!!

            Glide
                .with(this)
                .load(imageUri)
                ?.placeholder(R.drawable.baseline_person_24)
                ?.into(recipe_iv)


        }

    }

    companion object{
        const val GALLERY = 2
    }

    private fun showRationaleDialogForPermissions(){

        AlertDialog.Builder(this).
        setMessage("turn off")
            .setPositiveButton("GO TO SETTINGS"){
                    _,_ ->
                try {

                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package",packageName,null)
                    intent.data = uri
                    startActivity(intent)
                }catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }

            }.setNegativeButton("Cancel"){dialog,which ->
                dialog.dismiss()

            }.show()
    }

    fun imageUploadSuccess(imageUri:String){

        imageUrl = imageUri


    }

    fun recipeRegisterSuccess(){

        hideDialog()
        finish()
    }

    fun addRecipe(){

        showDialog()

        val sharedPreferences =
            getSharedPreferences("name", Context.MODE_PRIVATE)
        val uploader = sharedPreferences.getString("key","")!!

        val sharedPreference1 = getSharedPreferences("name1",Context.MODE_PRIVATE)
        val uploaderImage = sharedPreference1.getString("key1","")!!


        if(recipe_xorceuli.isChecked){

                category = "ხორცეული"
            }
        else if (recipe_deserti.isChecked){

              category = "დესერტი"
            }

            else if (recipe_comeuli.isChecked){

                category = "ცომეული"
            }

            else if (recipe_pasta.isChecked){

                category = "პასტა"
            }

            else if (recipe_zgvis_produqtebi.isChecked){

                category = "ზღვის პროდუქტები"
            }

            else if (recipe_bostneuli.isChecked){

                category = "ბოსტნეული"
            }

            else if (recipe_wvniani.isChecked){

                category = "წვნიანი"
            }

            else if (recipe_salatebi.isChecked){

                category = "სალათები"
            }


            else if (recipe_sousebi.isChecked){

                category = "სოუსები"
            }


        if(martivi.isChecked){

            difficulty = "მარტივი"
        }

        else if(sashualo.isChecked){

            difficulty = "საშუალო"

        }
        else if(rtuli.isChecked){

            difficulty = "რთული"

        }

        if(imageUri!=null && recipe_dasaxeleba.text.isNotEmpty()&&category.isNotEmpty()
            &&recipe_dro.text.isNotEmpty()&&difficulty.isNotEmpty()&&recipe_ingredientebi.text.isNotEmpty()
            &&recipe_momzadebis_wesi.text.isNotEmpty()){
            val recipe = Recipe(

                imageUri.toString(),
                recipe_dasaxeleba.text.toString(),
                category,
                recipe_dro.text.toString(),
                difficulty,
                recipe_ingredientebi.text.toString(),
                recipe_momzadebis_wesi.text.toString(),
                uploader,
                uploaderImage,
                FirestoreClass().getCurrentUserId()
            )

            FirestoreClass().registerRecipe(this@AddRecipeActivity,recipe)
            FirestoreClass().uploadImageToCloudStorage(this@AddRecipeActivity,imageUri!!,Constants.RECIPEIMAGE)
        }else{

            toast()
            hideDialog()
        }



    }




}