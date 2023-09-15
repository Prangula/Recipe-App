package com.example.foodapp.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.foodapp.Constants
import com.example.foodapp.FirestoreClass
import com.example.foodapp.R
import com.example.foodapp.models.Users1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {

    private var gender:String = ""
    private var imageUri:Uri? = null
    private var imageUrl:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        toolbar()


        register_button.setOnClickListener{

            registration()
    }

        register_iv.setOnClickListener {

            gallery()
        }

    }

    private fun toolbar(){
        setSupportActionBar(toolbar_register)

        toolbar_register.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        toolbar_register.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    fun registerSuccess(){

        hideDialog()
    }

    fun imageUploadSuccess(imageUri:String){

        imageUrl = imageUri

    }


    private fun gallery(){

        Dexter.withActivity(this)
            .withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object:MultiplePermissionsListener{
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
                ?.into(register_iv)


        }

    }

companion object{
    const val GALLERY = 1
}

  private fun registration(){

        if(register_name.text.isNotEmpty()&&register_surname.text.isNotEmpty()
            &&register_email.text.isNotEmpty()&&register_mobile.text.isNotEmpty()
            &&register_password.text.isNotEmpty()&&imageUri!=null){

            if (male.isChecked){

                gender = "კაცი"

            }else{

                gender = "ქალი"
            }

            FirestoreClass().uploadImageToCloudStorage(this@RegisterActivity,imageUri!!,
                Constants.IMAGE
            )

            showDialog()

            val email = register_email.text.toString().trim(){it<= ' '}
            val password = register_password.text.toString()

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener {task->

                    if(task.isSuccessful){

                        val firebaseUser:FirebaseUser = task.result!!.user!!

                        val user = Users1(

                            firebaseUser.uid,
                            register_name.text.toString(),
                            register_surname.text.toString(),
                            register_email.text.toString(),
                            register_mobile.text.toString(),
                            imageUri.toString(),
                            gender,
                            )

                        FirestoreClass().registerUser(this@RegisterActivity,user)
                        FirebaseAuth.getInstance().signOut()
                        finish()
                    }else{

                        Toast.makeText(this,task.exception!!.message,Toast.LENGTH_LONG)
                            .show()
                        hideDialog()
                    }
                }


        }
        else{
            toast()

        }



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