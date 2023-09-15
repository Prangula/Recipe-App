package com.example.foodapp.activities

import android.os.Bundle
import android.widget.Toast
import com.example.foodapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        toolbar()

        forgot_button.setOnClickListener {

            password()
        }
    }


    private fun toolbar(){
        setSupportActionBar(toolbar_password)

        toolbar_password.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        toolbar_password.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun password(){

        if(forgot_email.text.isNotEmpty()){

            showDialog()

            val email = forgot_email.text.toString().trim(){it<= ' '}

            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task->

                    if(task.isSuccessful){


                        Toast.makeText(this@ForgotPasswordActivity,
                            "იმეილი წარმატებით გამოიგზავნა", Toast.LENGTH_LONG)
                            .show()
                        hideDialog()
                        finish()
                    }else{

                        Toast.makeText(this,task.exception!!.message,Toast.LENGTH_SHORT)
                            .show()
                        hideDialog()
                    }
                }


        }else{
            toast()
        }

    }

}