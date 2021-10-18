package com.example.softcom_challenge

import ParentAdapter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.francisco.projeto_final_PDMO.MyAdapter.OperacoesAdapter
import com.example.softcom_challenge.Adapter.ScrollStateHolder
import com.example.softcom_challenge.Models.Category
import com.example.softcom_challenge.Models.Product
import com.example.softcom_challenge.Models.Sectors
import com.example.softcom_challenge.Views.HomeScreen
import com.example.softcom_challenge.Views.RequestScreen
import com.example.softcom_challenge.Views.SelectScreen
import com.google.android.material.bottomnavigation.BottomNavigationView



class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeScreen()
    private val requestScreen = RequestScreen()
    private val selectScreen = SelectScreen()

    private fun hideSystemUI() {
        // Ativa o modo imersivo de tela
        // Para o modo "recuar", remova SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Esconde barra de status e barra de navegação
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN

                )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideSystemUI()
        replaceFragment(homeFragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_homeFragment -> replaceFragment(homeFragment)
                R.id.ic_RequestFragment -> replaceFragment(requestScreen)
                R.id.ic_MoreFragment -> replaceFragment(selectScreen)
            }
            true
        }


    }
    private fun replaceFragment(fragment:Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.commit()
        }
    }
}

