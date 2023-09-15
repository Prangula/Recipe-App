package com.example.foodapp.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.*
import com.example.foodapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment() {

    private var detail:Users1= Users1()
    private var _binding:FragmentProfileBinding? = null
    private val binding get() = _binding!!




    private fun getUserDetails(){

        showDialog1()
        FirestoreClass().userProfile(this@ProfileFragment)

    }

    fun userDetailSuccess(users1: Users1){

        hideDialog1()
        detail = users1

        profile_name.text = detail.name
        profile_surname.text = detail.surname
        profile_email.text = detail.email
        profile_mobile.text = detail.mobile
        profile_gender.text = detail.gender
        Glide
            .with(this)
            .load(detail.image)
            .into(profile_iv)
    }

    override fun onResume() {
        super.onResume()
        getUserDetails()
    }




    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        notificationsViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        profile_sign_out1.setOnClickListener {

            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity,LoginActivity::class.java)
            intent.flags =Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)


        }


    }



}

