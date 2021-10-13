package com.example.softcom_challenge

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.francisco.projeto_final_PDMO.MyAdapter.OperacoesAdapter
import com.example.softcom_challenge.Models.Sectors


class MainActivity : AppCompatActivity() {

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

        var OperationsLista:ArrayList<Sectors> = ArrayList()
        val Comedouros = Sectors(title = "Comedouros", image = R.drawable.food_icon)
        OperationsLista.add(Comedouros)
        val Camas = Sectors(title = "Camas", image = R.drawable.bed_icon)
        OperationsLista.add(Camas)
        val House = Sectors(title = "Casas", image = R.drawable.house_icon)
        OperationsLista.add(House)
        val Toy = Sectors(title = "Brinquedos", image = R.drawable.toy_icon)
        OperationsLista.add(Toy)

        println(OperationsLista.size)

        val HorizontalList = findViewById<RecyclerView>(R.id.horizontal_recycler)
        HorizontalList.adapter = OperacoesAdapter(OperationsLista)
        val mLayoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)
        HorizontalList.setLayoutManager(mLayoutManager)
    }
}