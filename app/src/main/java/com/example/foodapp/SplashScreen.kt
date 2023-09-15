package com.example.foodapp

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import com.example.foodapp.ui.dashboard.HomeFragment
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlin.random.Random

class SplashScreen : AppCompatActivity() {

    private lateinit var animation:AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        iv_splash.setBackgroundResource(R.drawable.animation)
        animation = iv_splash.background as AnimationDrawable
        animation.start()


        Handler().postDelayed({

            if(FirestoreClass().getCurrentUserId().isNotEmpty()){

                val intent = Intent(this,NavigationActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {


                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()


            }

        },2000)

    }




}