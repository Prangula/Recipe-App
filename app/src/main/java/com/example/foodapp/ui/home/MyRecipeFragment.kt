package com.example.foodapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.*
import com.example.foodapp.adapters.MyRecipeAdapter
import com.example.foodapp.databinding.FragmentMyrecipeBinding
import com.example.foodapp.models.Recipe

import kotlinx.android.synthetic.main.fragment_myrecipe.*


class MyRecipeFragment : BaseFragment() {

    private var _binding: FragmentMyrecipeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding =FragmentMyrecipeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        homeViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun rvSuccess(items:ArrayList<Recipe>){

        if(items.size>0){

            hideDialog1()
            val adapter = MyRecipeAdapter(items,this)
            myRecipe_recyclerview.adapter=adapter
            myRecipe_recyclerview.layoutManager = GridLayoutManager(activity,2)

            myRecipe_text_no.visibility = View.GONE

        }
      else{

          hideDialog1()

            myRecipe_text_no.visibility = View.VISIBLE
        }


    }


   private fun getMyRecipe(){

        showDialog1()
        FirestoreClass().getMyRecipe(this@MyRecipeFragment)
    }

    override fun onResume() {
        super.onResume()
        getMyRecipe()

    }


}