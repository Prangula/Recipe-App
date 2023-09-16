package com.example.foodapp.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.*
import com.example.foodapp.activities.AddRecipeActivity
import com.example.foodapp.adapters.RecipeAdapter

import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.models.Recipe
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.Date

class HomeFragment : BaseFragment() {


    private var _binding: FragmentHomeBinding? = null
    private var selectedCategory: String = "ყველა" // Default category







    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dashboardViewModel.text.observe(viewLifecycleOwner) {

        }

        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        home_add.setOnClickListener{

            val intent = Intent(activity, AddRecipeActivity::class.java)
            startActivity(intent)

        }

        home_xorceuli.setOnCheckedChangeListener { _, _ -> updateUI()
        home_et.text.clear()}
        home_deserti.setOnCheckedChangeListener { _, _ -> updateUI()
            home_et.text.clear()}
        home_comeuli.setOnCheckedChangeListener { _, _ -> updateUI()
            home_et.text.clear()}
        home_pasta.setOnCheckedChangeListener { _, _ -> updateUI()
            home_et.text.clear()}
        home_zgvis_produqtebi.setOnCheckedChangeListener { _, _ -> updateUI()
            home_et.text.clear()}
        home_bostneuli.setOnCheckedChangeListener { _, _ -> updateUI()
            home_et.text.clear()}
        home_wvniani.setOnCheckedChangeListener { _, _ -> updateUI()
            home_et.text.clear()}
        home_salatebi.setOnCheckedChangeListener { _, _ -> updateUI()
            home_et.text.clear()}
        home_sousebi.setOnCheckedChangeListener { _, _ -> updateUI()
            home_et.text.clear()}
        home_yvela.setOnCheckedChangeListener { _, _ -> updateUI()
            home_et.text.clear()}

        home_et.addTextChangedListener {
            // ჩაწერისას ეგრევე რო გამოჩნდეს რაც გვინდა
           updateUI1()

        }

        home_et.setOnEditorActionListener { textView, i, keyEvent ->
            val imm = home_et.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(home_et.windowToken, 0)

        }

    }


    private fun updateUI() {

        // კატეგორიები რო დააფდეითდეს
        getRecipes()
        hideDialog1()

    }

    private fun updateUI1() {
        if (home_et.text.isNotEmpty()) {
            // If home_et is not empty, clear radio button checks and apply filter
            home_radioGroup.clearCheck()
            getRecipes()
            hideDialog1()
        } else {
            // If home_et is empty, check home_yvela radio button and get all items
            home_yvela.isChecked = true
            getRecipes()
            hideDialog1()
        }
    }



    fun rvSuccess(items:ArrayList<Recipe>){

        val category = selectedCategory()

        val filter1 =

            if(category == "ყველა"){
            items

        }
            else if(home_et.text.isNotEmpty()){

                    items.filter { it.title.take(3) == home_et.text.toString().take(3)}
            }

            else {

                items.filter { it.category == category }

            }

       val sort = filter1.sortedByDescending {it.date}


        if(items.size>0){

            hideDialog1()

            val adapter = RecipeAdapter(ArrayList(sort),this@HomeFragment)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(activity,2)
            home_text_no.visibility = View.GONE
        }


            else{
            hideDialog1()

            home_text_no.visibility = View.VISIBLE
        }


    }

    fun selectedCategory():String {

        selectedCategory = when {

            home_xorceuli.isChecked -> {
                "ხორცეული"
            }
            home_deserti.isChecked -> {
                "დესერტი"
            }

            home_comeuli.isChecked -> {
                "ცომეული"
            }

            home_pasta.isChecked -> {
                "პასტა"
            }

            home_zgvis_produqtebi.isChecked -> {
                "ზღვის პროდუქტები"
            }
            home_bostneuli.isChecked -> {
                "ბოსტნეული"
            }
            home_wvniani.isChecked -> {
                "წვნიანი"
            }
            home_salatebi.isChecked -> {

                "სალათები"
            }
            home_sousebi.isChecked -> {
                "სოუსები"
            }

            home_yvela.isChecked -> {
                "ყველა"
            }

            else -> ({
                null
            }).toString()
        }

        return selectedCategory

    }


    private fun getRecipes(){

        showDialog1()
        FirestoreClass().getRecipe(this@HomeFragment)

    }

    override fun onResume() {
        super.onResume()
        getRecipes()

    }







}