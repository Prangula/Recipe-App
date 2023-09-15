package com.example.foodapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

open class BaseActivity : AppCompatActivity() {
    private var dialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }


    fun showDialog(){

        dialog = Dialog(this)
        dialog!!.setCancelable(false)
        dialog!!.setContentView(R.layout.dialog)

        dialog!!.show()


    }

   fun hideDialog(){

       if (!isFinishing && dialog != null && dialog!!.isShowing) {
           dialog!!.dismiss()
       }
    }

    fun toast(){

        Toast.makeText(this,"გთხოვთ შეავსოთ ყველა ველი", Toast.LENGTH_LONG)
            .show()

    }

}