package com.example.foodapp.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import com.bumptech.glide.Glide
import com.example.foodapp.Constants
import com.example.foodapp.FirestoreClass
import com.example.foodapp.R
import com.example.foodapp.models.Users1
import com.example.foodapp.ui.notifications.ProfileFragment
import com.google.firebase.firestore.auth.User
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : BaseActivity() {

    private var detail:Users1 = Users1()
    private var imageUri:Uri? = null
    private var imageUrl:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        toolbar()

        if(intent.hasExtra(Constants.DETAIL)){


            detail = intent.getParcelableExtra(Constants.DETAIL)!!

        }

        update_name.setText(detail.name)
        update_surname.setText(detail.surname)
        update_email.setText(detail.email)
        update_mobile.setText(detail.mobile)

        if(detail.gender=="კაცი"){


            male_update.isChecked = true
        }else{

            female_update.isChecked = true
        }

        Glide
            .with(this)
            .load(detail.image)
            .into(update_iv)


        update_button.setOnClickListener {


            showDialog()
            if(imageUri!=null){
                FirestoreClass().uploadImageToCloudStorage(this@UpdateActivity,imageUri!!,Constants.IMAGE)

            }
            updateUser()


        }

        update_iv.setOnClickListener {

            gallery()
        }



    }

    private fun toolbar(){

        setSupportActionBar(toolbar_update)
        toolbar_update.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        toolbar_update.setNavigationOnClickListener {
            onBackPressed()
        }

    }


    fun updateSuccess(){

        hideDialog()
        val intent = Intent(this,NavigationActivity::class.java)
        startActivity(intent)
        finish()


    }

    fun imageUploadSuccess(imageUri:String){

        imageUrl = imageUri
        updateUser()


    }

    fun updateUser(){

        var hashmap = HashMap<String,Any>()

        val name = update_name.text.toString()

        if(name.isNotEmpty()){

            hashmap["name"] = name

        }else{
            toast()
        }

        val surname = update_surname.text.toString()

        if(surname.isNotEmpty()){

            hashmap["surname"] = surname
        }
        else{
            toast()
        }

        val email = update_email.text.toString()

        if(email.isNotEmpty()){

            hashmap["email"] = email
        }
        else{
            toast()
        }

        val mobile = update_mobile.text.toString()

        if(mobile.isNotEmpty()){

            hashmap["mobile"] = mobile
        }
        else{
            toast()
        }

        val gender =
            if(male_update.isChecked){

                "კაცი"
            }
        else{

            "ქალი"
            }

        hashmap["gender"] = gender


        if(imageUrl.isNotEmpty()&&imageUri!=null){
            hashmap["image"] = imageUrl
        }



        FirestoreClass().updateProfile(this@UpdateActivity,hashmap)






    }

    private fun gallery(){

        Dexter.withActivity(this)
            .withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if(report!!.areAllPermissionsGranted()){

                        val galleryIntent = Intent(Intent.ACTION_PICK,
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

        if(resultCode== Activity.RESULT_OK&&requestCode== GALLERY &&data!!.data!=null){

            imageUri = data!!.data!!

            Glide
                .with(this)
                .load(imageUri)
                ?.placeholder(R.drawable.baseline_person_24)
                ?.into(update_iv)


        }

    }

    companion object {

        const val GALLERY = 3
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




}