package com.example.foodapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.foodapp.Constants
import com.example.foodapp.FirestoreClass
import com.example.foodapp.R
import com.example.foodapp.models.Users1
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        login_register.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }

        forgot_password.setOnClickListener {

            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)

       }

        login_btn.setOnClickListener {
            logIn()
        }

    }

    private fun logIn(){

        if(login_email.text.isNotEmpty()&&login_password.text.isNotEmpty()){

            showDialog()

            val email = login_email.text.toString().trim(){it<= ' '}
            val password = login_password.text.toString()

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){

                        FirestoreClass().logInUser(this)

                    }else{

                        hideDialog()
                        Toast.makeText(this,task.exception!!.message,Toast.LENGTH_SHORT
                            ).show()
                    }
                }



        }
        else{
            toast()
        }


    }

    fun logInSuccess(users: Users1){

        hideDialog()
        val intent = Intent(this, NavigationActivity::class.java)
        intent.putExtra(Constants.DETAIL,users)
        startActivity(intent)
        finish()


    }
}